package engine;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.bullet.objects.VehicleWheel;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import engine.physics.PhysicsGame;

public class CarRunGame extends SimpleApplication implements ActionListener {

    private BulletAppState bulletAppState;
    private VehicleControl player;
    private VehicleWheel fr, fl, br, bl;
    private Node node_fr, node_fl, node_br, node_bl;
    private float wheelRadius;
    private float steeringValue = 0;
    private float accelerationValue = 0;
    private Node carNode;
    private Node camNode;
    private int voltas = 0;
    private boolean volta1 = false,volta2 = false;
    
    public static void main(String[] args) {
        CarRunGame app = new CarRunGame();
        app.showSettings = false;
        AppSettings settings = new AppSettings(true);
        settings.put("Width", 1280);
        settings.put("Height", 720);
        settings.put("Title", "Car Run");
        settings.put("VSync", false); //Anti-Aliasing
        settings.put("Samples", 4);
        app.setSettings(settings);
        app.start();
    }

    private void setupKeys() {
        inputManager.addMapping("Lefts", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Rights", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("Ups", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("Downs", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Reset", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addListener(this, "Lefts");
        inputManager.addListener(this, "Rights");
        inputManager.addListener(this, "Ups");
        inputManager.addListener(this, "Downs");
        inputManager.addListener(this, "Space");
        inputManager.addListener(this, "Reset");
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        setDisplayFps(false);
        setDisplayStatView(false);
//        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
//        cam.setFrustumFar(150f);
//        flyCam.setMoveSpeed(10);

        setupKeys();
        PhysicsGame.createPhysicsTestWorld(rootNode, assetManager, bulletAppState.getPhysicsSpace());
//        setupFloor();
        buildPlayer();

        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.5f, -1f, -0.3f).normalizeLocal());
        rootNode.addLight(dl);

        dl = new DirectionalLight();
        dl.setDirection(new Vector3f(0.5f, -0.1f, 0.3f).normalizeLocal());
        //  rootNode.addLight(dl);
    }

    private PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }

//    public void setupFloor() {
//        Material mat = assetManager.loadMaterial("Textures/Terrain/BrickWall/BrickWall.j3m");
//        mat.getTextureParam("DiffuseMap").getTextureValue().setWrap(WrapMode.Repeat);
////        mat.getTextureParam("NormalMap").getTextureValue().setWrap(WrapMode.Repeat);
////        mat.getTextureParam("ParallaxMap").getTextureValue().setWrap(WrapMode.Repeat);
//
//        Box floor = new Box(Vector3f.ZERO, 140, 1f, 140);
//        floor.scaleTextureCoordinates(new Vector2f(112.0f, 112.0f));
//        Geometry floorGeom = new Geometry("Floor", floor);
//        floorGeom.setShadowMode(ShadowMode.Receive);
//        floorGeom.setMaterial(mat);
//
//        PhysicsNode tb = new PhysicsNode(floorGeom, new MeshCollisionShape(floorGeom.getMesh()), 0);
//        tb.setLocalTranslation(new Vector3f(0f, -6, 0f));
////        tb.attachDebugShape(assetManager);
//        rootNode.attachChild(tb);
//        getPhysicsSpace().add(tb);
//    }
    private Geometry findGeom(Spatial spatial, String name) {
        if (spatial instanceof Node) {
            Node node = (Node) spatial;
            for (int i = 0; i < node.getQuantity(); i++) {
                Spatial child = node.getChild(i);
                Geometry result = findGeom(child, name);
                if (result != null) {
                    return result;
                }
            }
        } else if (spatial instanceof Geometry) {
            if (spatial.getName().startsWith(name)) {
                return (Geometry) spatial;
            }
        }
        return null;
    }

    private void buildPlayer() {
        float stiffness = 120.0f;//200=f1 car
        float compValue = 0.2f; //(lower than damp!)
        float dampValue = 0.3f;
        final float mass = 400;

        //Load model and get chassis Geometry
        carNode = (Node) assetManager.loadModel("Models/Ferrari/Car.scene");
        carNode.setShadowMode(ShadowMode.Cast);
        Geometry chasis = findGeom(carNode, "Car");
        BoundingBox box = (BoundingBox) chasis.getModelBound();

        //Create a hull collision shape for the chassis
        CollisionShape carHull = CollisionShapeFactory.createDynamicMeshShape(chasis);

        //Create a vehicle control
        player = new VehicleControl(carHull, mass);
        carNode.addControl(player);

        //Setting default values for wheels
        player.setSuspensionCompression(compValue * 2.0f * FastMath.sqrt(stiffness));
        player.setSuspensionDamping(dampValue * 2.0f * FastMath.sqrt(stiffness));
        player.setSuspensionStiffness(stiffness);
        player.setMaxSuspensionForce(10000);

        //Create four wheels and add them at their locations
        //note that our fancy car actually goes backwards..
        Vector3f wheelDirection = new Vector3f(0, -1, 0);
        Vector3f wheelAxle = new Vector3f(-1, 0, 0);

        Geometry wheel_fr = findGeom(carNode, "WheelFrontRight");
        wheel_fr.center();
        box = (BoundingBox) wheel_fr.getModelBound();
        wheelRadius = box.getYExtent();
        float back_wheel_h = (wheelRadius * 1.7f) - 1f;
        float front_wheel_h = (wheelRadius * 1.9f) - 1f;
        player.addWheel(wheel_fr.getParent(), box.getCenter().add(0, -front_wheel_h, 0),
                wheelDirection, wheelAxle, 0.2f, wheelRadius, true);

        Geometry wheel_fl = findGeom(carNode, "WheelFrontLeft");
        wheel_fl.center();
        box = (BoundingBox) wheel_fl.getModelBound();
        player.addWheel(wheel_fl.getParent(), box.getCenter().add(0, -front_wheel_h, 0),
                wheelDirection, wheelAxle, 0.2f, wheelRadius, true);

        Geometry wheel_br = findGeom(carNode, "WheelBackRight");
        wheel_br.center();
        box = (BoundingBox) wheel_br.getModelBound();
        player.addWheel(wheel_br.getParent(), box.getCenter().add(0, -back_wheel_h, 0),
                wheelDirection, wheelAxle, 0.2f, wheelRadius, false);

        Geometry wheel_bl = findGeom(carNode, "WheelBackLeft");
        wheel_bl.center();
        box = (BoundingBox) wheel_bl.getModelBound();
        player.addWheel(wheel_bl.getParent(), box.getCenter().add(0, -back_wheel_h, 0),
                wheelDirection, wheelAxle, 0.2f, wheelRadius, false);

        player.getWheel(2).setFrictionSlip(4);
        player.getWheel(3).setFrictionSlip(4);

        camNode = new CameraNode("Camera Node", cam);
        carNode.attachChild(camNode);
        camNode.setLocalTranslation(new Vector3f(0, +5, +15));
        rootNode.attachChild(carNode);
        camNode.lookAt(carNode.getLocalTranslation(), Vector3f.UNIT_Y);
        getPhysicsSpace().add(player);
    }

    public void onAction(String binding, boolean value, float tpf) {
        if (binding.equals("Lefts")) {
            if (value) {
                steeringValue += .5f;
            } else {
                steeringValue += -.5f;
            }
            player.steer(steeringValue);
        } else if (binding.equals("Rights")) {
            if (value) {
                steeringValue += -.5f;
            } else {
                steeringValue += .5f;
            }
            player.steer(steeringValue);
        } //note that our fancy car actually goes backwards..
        else if (binding.equals("Ups")) {
            if (value) {
                accelerationValue -= 800;
            } else {
                accelerationValue += 800;
            }
            player.accelerate(accelerationValue);
            player.setCollisionShape(CollisionShapeFactory.createDynamicMeshShape(findGeom(carNode, "Car")));
        } else if (binding.equals("Downs")) {
            if (value) {
                player.brake(40f);
            } else {
                player.brake(0f);
            }
        } else if (binding.equals("Reset")) {
            if (value) {
                System.out.println("Reset");
                player.setPhysicsLocation(Vector3f.ZERO);
                player.setPhysicsRotation(new Matrix3f());
                player.setLinearVelocity(Vector3f.ZERO);
                player.setAngularVelocity(Vector3f.ZERO);
                player.resetSuspension();
            } else {
            }
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        verificarVolta();
    }
    
    public void verificarVolta(){
        if(!volta2){
            volta2 = validarVolta(2);
        }else {
            volta1 = validarVolta(1);
        }
            
        if(volta1 && volta2){
            volta1 = false;
            volta2 = false;
            voltas++;
        }
        
        System.out.println(voltas);
    }
    
    public boolean validarVolta(int verify){
        Vector3f pos = carNode.getLocalTranslation();
        
        if(verify == 1){
            if((pos.getX() >= -9 && pos.getX() < 4)  && (pos.getZ() >= -10 && pos.getZ() < 10)){
                return true;
            }
        }else{
            if((pos.getX() <= -150.0 && pos.getX() > -195)  && (pos.getZ() >= -7 && pos.getZ() < 27.9)){
                return true;
            }
        }
        
        return false;
    }
}
