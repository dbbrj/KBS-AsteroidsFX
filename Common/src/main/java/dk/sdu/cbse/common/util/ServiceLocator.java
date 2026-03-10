package dk.sdu.cbse.common.util;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public enum ServiceLocator {
    INSTANCE;

    private static final Map<Class, ServiceLoader> loadermap = new HashMap<>();
    private final ModuleLayer layer;

    ServiceLocator() {
        try {
            Path pluginsDir = Paths.get("plugins");

            ModuleFinder pluginsFinder = ModuleFinder.of(pluginsDir);

            List<String> plugins = pluginsFinder
                    .findAll()
                    .stream()
                    .map(ModuleReference::descriptor)
                    .map(ModuleDescriptor::name)
                    .collect(Collectors.toList());

            Configuration pluginsConfiguration = ModuleLayer
                    .boot()
                    .configuration()
                    .resolve(pluginsFinder, ModuleFinder.of(), plugins);


            layer = ModuleLayer
                    .boot()
                    .defineModulesWithOneLoader(pluginsConfiguration, ClassLoader.getSystemClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> locateAll(Class<T> service) {
        ServiceLoader<T> loader = loadermap.get(service);

        if (loader == null) {
            loader = ServiceLoader.load(layer, service);
            loadermap.put(service, loader);
        }

        List<T> list = new ArrayList<>();

        if (loader != null) {
            try {
                for (T instance : loader) {
                    list.add(instance);
                }
            } catch (ServiceConfigurationError serviceError) {
            }
        }
        return list;
    }
}
