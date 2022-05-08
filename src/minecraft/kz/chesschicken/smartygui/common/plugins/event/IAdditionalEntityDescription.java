package kz.chesschicken.smartygui.common.plugins.event;

import net.minecraft.src.Entity;

public interface IAdditionalEntityDescription {

	String[] getAdditionalEntityDescription(Entity entity);
	
}
