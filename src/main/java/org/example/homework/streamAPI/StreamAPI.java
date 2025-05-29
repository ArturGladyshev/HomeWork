package org.example.homework.streamAPI;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Класс хранит итератор для перебора элементов.
 * Конструкторы могут принимать коллекции, массивы и потоки. Объект создается через
 * статический метод, возвращающий экземпляр класса StreamAPI с новым итератором, либо ссылку this.
 */
public class StreamAPI<T> {

    private Iterator<T> entryIterator;

    public static <T> StreamAPI<T> of(Collection<T> collection) {
        return new StreamAPI<>(collection.iterator());
    }

    public static <T> StreamAPI<T> of(T[] array) {
        return new StreamAPI<>(Arrays.asList(array).iterator());
    }

    public static <T> StreamAPI<T> of(InputStream source) {
        ArrayList<T> objectList = new ArrayList<>();
        try (ObjectInputStream objectSource = new ObjectInputStream(source)) {
            for (Object ob = objectSource.readObject() ;; ob = objectSource.readObject() ) {
                objectList.add((T) ob);
            }
        } catch (IOException eo) {
            System.out.println("Достигнут конец файла");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StreamAPI<>(objectList.iterator());
    }

    private StreamAPI(Iterator<T> iterator) {
        entryIterator = iterator;
    }

    public StreamAPI<T> filter(Predicate<? super T> pred) {
        ArrayList<T> values = new ArrayList<>();
        while (entryIterator.hasNext()) {
            T val = entryIterator.next();
            if (pred.test(val)) {
                values.add(val);
            }
        }
        return new StreamAPI<>(values.iterator());
    }

    public <R> StreamAPI<R> transform(Function<? super T, ? extends R> func) {
        ArrayList<R> values = new ArrayList<>();
        while (entryIterator.hasNext()) {
            values.add(func.apply(entryIterator.next()));
        }
        return new StreamAPI<>(values.iterator());
    }

    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyFunction,
                                  Function<? super T, ? extends V> valueFunction) {
        Map<K, V> valuesMap = new HashMap<>();
        while (entryIterator.hasNext()) {
            T val = entryIterator.next();
            valuesMap.put(keyFunction.apply(val), valueFunction.apply(val));
        }
        return valuesMap;
    }

    public StreamAPI<T> distinct() {
        LinkedHashSet<T> valuesSet = new LinkedHashSet<>();
        while (entryIterator.hasNext()) {
            valuesSet.add(entryIterator.next());
        }
        entryIterator = valuesSet.iterator();
        return this;
    }

    public void forEach(Consumer<? super T> consumer) {
        while (entryIterator.hasNext()) {
            consumer.accept(entryIterator.next());
        }
    }

    public <E> StreamAPI<E> generate(Supplier<? extends E> supplier, int count) {
        ArrayList<E> values = new ArrayList<>();
        for (int j = 0; j < count; ++j) {
            values.add(supplier.get());
        }
        return new StreamAPI<>(values.iterator());
    }

    public T reduce(BinaryOperator<T> accum, T sum) {
        while (entryIterator.hasNext()) {
            sum = accum.apply(sum, entryIterator.next());
        }
        return sum;
    }

    public StreamAPI<T> sorted(Comparator<? super T> comp) {
        ArrayList<T> values = new ArrayList<>();
        while (entryIterator.hasNext()) {
            values.add(entryIterator.next());
        }
        Collections.sort(values, comp);
        return new StreamAPI<>(values.iterator());
    }

}