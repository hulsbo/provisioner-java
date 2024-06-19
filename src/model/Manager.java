package model;

import util.baseclass.ChildWrapper;

import java.util.*;
import java.util.stream.StreamSupport;

public class Manager {
    static final Map<UUID, BaseClass> index = new HashMap<>();

    public static void register(UUID id, BaseClass baseClass) {
        index.put(id, baseClass);
    }
    public static Set<BaseClass> findParents(UUID id) {
        Set<UUID> keys = index.keySet();
        Set<BaseClass> parents = new HashSet<>();

        for (UUID key : keys) {
            ChildWrapper elementOwnChild = index.get(key).childMap.get(id);

            if (elementOwnChild != null) {
                parents.add(index.get(key));
            }
        }
        return parents;
    }

    public static BaseClass getObject(UUID id) {
        return index.get(id);
    }

    public static void removeObject(UUID id) {
        index.remove(id);
    }
}

