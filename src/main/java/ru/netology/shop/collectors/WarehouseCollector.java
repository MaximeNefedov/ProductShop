package ru.netology.shop.collectors;

import ru.netology.shop.entites.ProductType;
import ru.netology.shop.entites.Product;
import ru.netology.shop.SortType;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class WarehouseCollector implements Collector<Map.Entry<String, List<Product>>, Map<ProductType, Map<Product, Integer>>, Map<ProductType, Map<Product, Integer>>> {
    private final SortType sortType;

    public WarehouseCollector(SortType sortType) {
        this.sortType = sortType;
    }

    @Override
    public Supplier<Map<ProductType, Map<Product, Integer>>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<ProductType, Map<Product, Integer>>, Map.Entry<String, List<Product>>> accumulator() {
        return (productsMap, entry) -> {
            List<Product> products = entry.getValue();
            if (products != null && !products.isEmpty()) {
                Product product = products.get(0);
                ProductType productType = product.getType();
                productsMap.compute(productType, (type, currentProductMap) -> {
                    if (currentProductMap == null) {
                        currentProductMap = new HashMap<>();
                    }
                    currentProductMap.compute(product, (name, count) -> {
                        int productCount = products.size();
                        if (count == null) {
                            count = productCount;
                        } else count += productCount;
                        return count;
                    });
                    return currentProductMap;
                });
            }
        };
    }

    @Override
    public BinaryOperator<Map<ProductType, Map<Product, Integer>>> combiner() {
        return (map1, map2) -> map1;
    }

    @Override
    public Function<Map<ProductType, Map<Product, Integer>>, Map<ProductType, Map<Product, Integer>>> finisher() {
        return sortType == SortType.NONE ? map -> map : map -> {
            Comparator<Map.Entry<Product, Integer>> comparator = null;
            switch (sortType) {
                case BY_PRICE_ASC -> comparator = Comparator.comparingDouble(entry -> entry.getKey().getPrice().doubleValue());
                case BY_PRICE_DESC -> comparator = Comparator.comparing(entry -> entry.getKey().getPrice().doubleValue(), Comparator.reverseOrder());

                case BY_RATING_ASC -> comparator = Comparator.comparingInt(entry -> entry.getKey().getRating());
                case BY_RATING_DESC -> comparator = Comparator.comparing(entry -> entry.getKey().getRating(), Comparator.reverseOrder());

                case BY_COUNT_ASC -> comparator = Comparator.comparingInt(Map.Entry::getValue);
                case BY_COUNT_DESC -> comparator = Map.Entry.comparingByValue(Comparator.reverseOrder());
            }
            for (ProductType productType : map.keySet()) {
                Map<Product, Integer> collect = map.get(productType).entrySet().stream()
                        .sorted(comparator)
                        .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (i1, i2) -> i1,
                                        LinkedHashMap::new
                                )
                        );
                map.put(productType, collect);
            }
            return map;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
