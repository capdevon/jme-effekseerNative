
import com.jme.effekseer.EffekseerEmitterControl;
import com.jme.effekseer.EffekseerPostRenderer;
import com.jme.effekseer.driver.EffekseerEmissionDriverGeneric;
import com.jme.effekseer.driver.fun.impl.EffekseerGenericDynamicInputSupplier;
import com.jme.effekseer.driver.fun.impl.EffekseerGenericSpawner;
import com.jme.effekseer.driver.fun.impl.EffekseerPointFollowingSpatialShape;
import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.BloomFilter.GlowMode;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.system.AppSettings;

import org.jesse.Jesse;
import org.jesse.JesseSpatial;
import org.jesse.JesseSpatial.JesseAnimations;

public class EffekseerTest extends SimpleApplication{

    @Override
    public void simpleInitApp() {
        JesseSpatial sp=Jesse.buildAndAttachScene(assetManager,rootNode);
        sp.playAnim(0,JesseAnimations.Run,true);
        
        EffekseerPostRenderer effekseerRenderer=new EffekseerPostRenderer(assetManager,settings.isGammaCorrection());
        // effekseerRenderer.setAsync(1);

        FilterPostProcessor fpp=new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        fpp.addFilter(effekseerRenderer);
        fpp.addFilter(new SSAOFilter(2.9299974f,32.920483f,5.8100376f,0.091000035f));

        BloomFilter bloom=new BloomFilter(GlowMode.Scene);
        bloom.setBloomIntensity(4f);
        fpp.addFilter(bloom);

        if(settings.getSamples()>0)fpp.setNumSamples(settings.getSamples());


        flyCam.setMoveSpeed(10);
        viewPort.setBackgroundColor(ColorRGBA.Black);
        
        EffekseerEmitterControl effekt=(EffekseerEmitterControl)assetManager.loadAsset("effekts/fire/fire5.efkefc");
        effekt.setDriver(
            new EffekseerEmissionDriverGeneric()
                .shape(new EffekseerPointFollowingSpatialShape())
                .spawner(new EffekseerGenericSpawner().loop(true).maxInstances(1))
                .dynamicInputSupplier(new EffekseerGenericDynamicInputSupplier().set(0,10f).set(1,11f))
        );
        sp.addControl(effekt);
        effekt.setScale(10f);

        cam.setLocation(new Vector3f(0,0,10));
        cam.lookAt(Vector3f.ZERO,Vector3f.UNIT_Y);

    }
    

     public static void main(String[] args) {
         AppSettings settings=new AppSettings(true);
         settings.setRenderer(AppSettings.LWJGL_OPENGL2);
         EffekseerTest app=new EffekseerTest();
        app.setSettings(settings);;
         app.start();
     }
}