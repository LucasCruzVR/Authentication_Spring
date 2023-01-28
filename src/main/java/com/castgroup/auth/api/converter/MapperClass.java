package com.castgroup.auth.api.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.collection.spi.PersistentCollection;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.ObjectUtils;

public class MapperClass {

    private MapperClass() {
    }

    public static <D, T> D converter(final T entity, Class<D> outClass) {
        if (ObjectUtils.isEmpty(entity))
            return null;

        var mapper = new ModelMapper();

        mapper.getConfiguration().setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection));

        try {
            return mapper.map(entity, outClass);
        } catch (Exception e) {
            Logger.getLogger(outClass.getName()).log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    public static <D, T> List<D> converter(final List<T> entity, Class<D> outClass) {
        if (ObjectUtils.isEmpty(entity))
            return Collections.emptyList();

        var mapper = new ModelMapper();
        mapper.getConfiguration()
                .setPropertyCondition(context -> (!(context.getSource() instanceof PersistentCollection)));

        var l = new ArrayList<D>(entity.size());
        entity.forEach(en -> {
            try {
                l.add(mapper.map(en, outClass));
            } catch (Exception e) {
                Logger.getLogger(outClass.getName()).log(Level.SEVERE, e.getMessage());
            }
        });

        return l;
    }

    public static <D, T> Set<D> converter(final Set<T> entity, Class<D> outClass) {
        if (ObjectUtils.isEmpty(entity))
            return Collections.emptySet();

        var mapper = new ModelMapper();
        mapper.getConfiguration()
                .setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection));

        var l = new HashSet<D>(entity.size());
        entity.forEach(en -> {
            try {
                l.add(mapper.map(en, outClass));
            } catch (Exception e) {
                Logger.getLogger(outClass.getName()).log(Level.SEVERE, e.getMessage());
            }
        });
        return l;
    }

    public static <D, T> Page<D> converter(final Page<T> entity, Class<D> outClass) {
        if (ObjectUtils.isEmpty(entity))
            return null;

        return new PageImpl<>(MapperClass.converter(entity.getContent(), outClass), entity.getPageable(), entity.getTotalElements());
    }
}
