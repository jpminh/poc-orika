package com.bnpp.cib.e2e.services;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import javax.persistence.OneToMany;
import javax.persistence.metamodel.StaticMetamodel;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JpaModelService {

    public Map<String, Object> getJpaModels() throws NoSuchFieldException {
        Map<String, Object> entities = new HashMap<>();

        Reflections reflections = new Reflections("com.bnpp.cib.e2e");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(StaticMetamodel.class);
        Set<Class<?>> entitiesClazz = annotated.stream().map(ann -> ann.getAnnotation(StaticMetamodel.class).value()).collect(Collectors.toSet());
        Set<Class<?>> rootEntities = new HashSet<>(entitiesClazz);
        Map<String, Object> entitiesAsMap = new HashMap<>();
        for (Class<?> eClazz : rootEntities) {
            entitiesAsMap.put(eClazz.getName(), new HashMap<>());
        }

        for (Class<?> clazz : annotated) {
            StaticMetamodel staticModel = clazz.getAnnotation(StaticMetamodel.class);
            Map<String, Object> clazzMap = (Map<String, Object>) entitiesAsMap.get(staticModel.value().getName());
            log.debug("Entity: " + staticModel.value().getName());
            for (Field field : clazz.getFields()) {
                if (isSimpleClass(field)) {
                    Type type = field.getGenericType();
                    if (type instanceof ParameterizedType) {
                        ParameterizedType ptype = (ParameterizedType) type;
                        containEntity(entitiesClazz, rootEntities, ptype.getRawType(), ptype.getActualTypeArguments()[1], ptype.getActualTypeArguments()[0], field, entitiesAsMap);
                    } else {
                        clazzMap.put(field.getName(), field.getType().getSimpleName());
                    }
                }
            }
        }

        for (Class<?> clazz : rootEntities) {
            String key = clazz.getName();
            entities.put(key, entitiesAsMap.get(key));
        }

        return entities;
    }

    private boolean isSimpleClass(Field field) {
        return true;
    }

    private boolean containEntity(Set<Class<?>> entitiesClazz, Set<Class<?>> rootEntities, Type rawType, Type clazz, Type eClazz, Field field, Map<String, Object> entitiesAsMap) throws NoSuchFieldException {
        Map<String, Object> e = (Map<String, Object>) entitiesAsMap.get(eClazz.getTypeName());
        if (entitiesClazz.contains(clazz)) {
            Optional<Class<?>> optClazz = entitiesClazz.stream().filter(entityClazz -> entityClazz.equals(eClazz)).findFirst();
            if (optClazz.isPresent()) {
                Class<?> cl = optClazz.get();
                Field f = cl.getDeclaredField(field.getName());
                Annotation[] annotations = f.getAnnotations();
                for (Annotation ann : annotations) {
                    log.info(ann.toString());
                    if (ann instanceof OneToMany && ((OneToMany) ann).mappedBy() != null) {
                        log.debug("Remove entity {} from root", clazz);
                        rootEntities.remove(clazz);
                        Object sub = entitiesAsMap.get(clazz.getTypeName());
                        log.debug(e.toString());

                        if ("javax.persistence.metamodel.SingularAttribute".equals(rawType.getTypeName())) {
                            e.put(field.getName(), sub);
                        } else {
                            e.put(field.getName(), new Object[]{sub});
                        }
                        return true;
                    }
                }
            }
        }

        if ("javax.persistence.metamodel.SingularAttribute".equals(rawType.getTypeName())) {
            e.put(field.getName(), clazz.getTypeName());
        } else {
            e.put(field.getName(), new Object[]{clazz.getTypeName()});
        }
        return false;
    }

}
