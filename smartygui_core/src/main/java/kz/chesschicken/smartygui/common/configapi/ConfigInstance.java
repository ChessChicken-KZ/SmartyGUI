package kz.chesschicken.smartygui.common.configapi;

public abstract class ConfigInstance {
    protected Configuration instance;

    public ConfigInstance(String name)
    {
        instance = new Configuration(name);
    }

    public void start()
    {
        if(!instance.exists()) {
            saveConfig();
            instance.save();
        }
        else {
            instance.load();
            applyConfig();
        }
    }

    public Object getValue(String group, String property)
    {
        return instance.getGroup(group).getProperty(property);
    }

    public abstract void saveConfig();
    public void applyConfig() { }
}
