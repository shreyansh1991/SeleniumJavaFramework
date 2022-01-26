package factories;

import enums.EnvType;
import utils.PropertyUtil;

import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

public class EnvFactory {

    private EnvFactory() {
    }

    private static final Map<EnvType, Supplier<String>> envMap = new EnumMap<>(EnvType.class);

    private static final Supplier<String> stageSupplier = () -> {
        Properties properties = PropertyUtil.loadPropertiesUsingClassLoader("config_stage.properties");
        return properties.getProperty("url");
    };

    private static final Supplier<String> prodSupplier = () -> {
        Properties properties = PropertyUtil.loadPropertiesUsingClassLoader("config_prod.properties");
        return properties.getProperty("url");
    };

    static {
        envMap.put(EnvType.STAGE, stageSupplier);
        envMap.put(EnvType.PROD, prodSupplier);
    }

    public static final String getURL(EnvType type) {
        return envMap.get(type).get();
    }

}
