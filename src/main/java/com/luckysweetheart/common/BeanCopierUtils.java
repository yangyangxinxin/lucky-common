package com.luckysweetheart.common;

import com.luckysweetheart.common.pager.PagedResult;
import net.sf.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author yangxin
 */
public class BeanCopierUtils {
    /**
     * 由于创建BeanCopier对性能消耗，将其放入内存中方便调用
     */
    private static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

    public static void copy(Object source, Object target) {
        String beanKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = null;
        if (!beanCopierMap.containsKey(beanKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(beanKey, copier);
        } else {
            copier = beanCopierMap.get(beanKey);
        }
        copier.copy(source, target, null);
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + " to " + class2.toString();
    }

    /**
     * 返回 Model 的Map对象
     *
     * @param entityMap 原对象Map集合
     * @param modelClass 要转换的对象
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <Key, Entity, Model> Map<Key, Model> entityMapToModelMap(Map<Key, Entity> entityMap,
                                                                           Class<Model> modelClass) throws IllegalAccessException, InstantiationException {
        Map<Key, Model> modelMap = new HashMap<>();
        for (Key key : entityMap.keySet()) {
            Entity entity = entityMap.get(key);
            Model model = entityToModel(entity, modelClass);
            modelMap.put(key, model);
        }
        return modelMap;
    }

    /**
     * 返回Model的List集合
     *
     * @param entityList 原对象List
     * @param modelClass 要转换的对象
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <Entity, Model> List<Model> entityListToModelList(List<Entity> entityList, Class<Model> modelClass)
            throws IllegalAccessException, InstantiationException {
        List<Model> modelList = new ArrayList<>();
        for (Entity entity : entityList) {
            Model model = entityToModel(entity, modelClass);
            modelList.add(model);
        }
        return modelList;
    }

    /**
     * 返回Model 的PagedResult对象
     *
     * @param entityPagedResult 原对象PagedResult
     * @param modelClass 要转换的对象
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <Entity, Model> PagedResult<Model> getModelPagedResult(PagedResult<Entity> entityPagedResult, Class<Model> modelClass) throws InstantiationException, IllegalAccessException {
        PagedResult<Model> result = new PagedResult<>();
        if (entityPagedResult != null && entityPagedResult.getSize() > 0) {
            List<Model> models = entityListToModelList(entityPagedResult.getResults(), modelClass);
            result.setPaged(entityPagedResult.getPaged());
            result.setResults(models);
        }
        return result;
    }

    /**
     * 返回Model对象
     *
     * @param entity 原对象
     * @param modelClass 要转换的对象
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <Entity, Model> Model entityToModel(Entity entity, Class<Model> modelClass)
            throws IllegalAccessException, InstantiationException {
        Model model = modelClass.newInstance();
        copy(entity, model);
        return model;
    }
}
