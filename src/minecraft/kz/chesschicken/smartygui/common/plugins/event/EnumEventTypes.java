/**
 * Copyright 2022 ChessChicken-KZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kz.chesschicken.smartygui.common.plugins.event;

/**
 * List of events, that can be used.
 * <br>
 * {@link EnumEventTypes#ADDITIONAL_BLOCK_DESCRIPTION} - see {@link IAdditionalBlockDescription}.
 * <br>
 * {@link EnumEventTypes#OVERRIDE_BLOCK_RENDER} - see {@link IOverrideBlockRender}.
 * <br>
 * {@link EnumEventTypes#ADDITIONAL_ENTITY_DESCRIPTION} - see {@link IAdditionalEntityDescription}.
 */
public enum EnumEventTypes {
	ADDITIONAL_BLOCK_DESCRIPTION,
	ADDITIONAL_ENTITY_DESCRIPTION,
	OVERRIDE_BLOCK_RENDER
}
