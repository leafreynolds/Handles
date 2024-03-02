/*
 * File updated ~ 8 - 10 - 2022 ~ Leaf
 */

package leaf.handles.forge.datagen.patchouli;

import leaf.handles.HandlesMod;
import leaf.handles.forge.datagen.patchouli.data.PatchouliProvider;
import net.minecraft.data.DataGenerator;

//
//  In-Game Documentation generator
//
public class HandlesPatchouliGen extends PatchouliProvider
{
	public HandlesPatchouliGen(DataGenerator generatorIn)
	{
		super(generatorIn, HandlesMod.MOD_ID);
	}

	@Override
	protected void collectInfoForBook()
	{

		PatchouliFunctionsCategory.collect(this.categories, this.entries);
		//PatchouliOSEventsCategory.collect(this.categories, this.entries);


	}

	/**
	 * Gets a name for this provider, to use in logging.
	 */
	public String getName()
	{
		return "Handles Patchouli";
	}

}



