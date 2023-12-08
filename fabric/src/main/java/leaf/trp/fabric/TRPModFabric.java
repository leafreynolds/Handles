package leaf.trp.fabric;

import leaf.trp.TRPMod;
import net.fabricmc.api.ModInitializer;

public class TRPModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TRPMod.init();
    }
}
