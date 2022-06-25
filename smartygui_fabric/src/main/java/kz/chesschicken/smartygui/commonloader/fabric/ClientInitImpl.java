package kz.chesschicken.smartygui.commonloader.fabric;

import kz.chesschicken.smartygui.commonloader.IMod;
import net.fabricmc.api.ClientModInitializer;
import org.jetbrains.annotations.ApiStatus;

import java.util.Objects;

public class ClientInitImpl<T extends IMod<T>> implements ClientModInitializer {

    protected T instance;

    public ClientInitImpl(T t) {
        this.instance = t;
        InjectHelper.register(this);
    }

    @ApiStatus.Internal
    @Override
    public void onInitializeClient() {
        this.instance.onInitializeClient();
    }

    @ApiStatus.Internal
    public T get() {
        return instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientInitImpl<?> that = (ClientInitImpl<?>) o;
        return Objects.equals(instance, that.instance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instance);
    }
}
