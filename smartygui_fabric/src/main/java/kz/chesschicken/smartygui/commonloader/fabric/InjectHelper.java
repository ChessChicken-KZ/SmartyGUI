package kz.chesschicken.smartygui.commonloader.fabric;

import kz.chesschicken.smartygui.commonloader.IMod;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

public class InjectHelper {
    private static Collection<ClientInitImpl<?>> collectionA = new HashSet<>();

    public static void register(ClientInitImpl<?> a) {
        collectionA.add(a);
    }

    public static void forEach(Consumer<IMod<?>> consumer) {
        collectionA.forEach(clientInit -> consumer.accept(clientInit.get()));
    }
}
