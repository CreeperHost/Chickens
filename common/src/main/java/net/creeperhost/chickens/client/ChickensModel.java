package net.creeperhost.chickens.client;

import net.creeperhost.chickens.Chickens;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ChickensModel extends EntityModel<RenderChickens.ChickensRenderState> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "chicken"), "main");
    private final ModelPart head;
    private final ModelPart wattle;
    private final ModelPart wattleLarge;
    private final ModelPart comb;
    private final ModelPart tail;
    private final ModelPart body;
    private final ModelPart leg0;
    private final ModelPart leg1;
    private final ModelPart wing0;
    private final ModelPart wing1;

    public ChickensModel(ModelPart root) {
        super(root);
        this.tail = root.getChild("tail");
        this.head = root.getChild("head");
        this.wattle = head.getChild("wattle");
        this.wattleLarge = head.getChild("wattle_large");
        this.comb = head.getChild("comb");
        this.body = root.getChild("body");
        this.leg0 = root.getChild("leg0");
        this.leg1 = root.getChild("leg1");
        this.wing0 = root.getChild("wing0");
        this.wing1 = root.getChild("wing1");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F), PartPose.offset(0.0F, 15.0F, -4.0F));
        PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F), PartPose.ZERO);
        PartDefinition wattle = head.addOrReplaceChild("wattle", CubeListBuilder.create().texOffs(14, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F), PartPose.ZERO);
        PartDefinition wattleLarge = head.addOrReplaceChild("wattle_large", CubeListBuilder.create().texOffs(14, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 3.0F, 2.0F), PartPose.ZERO);
        PartDefinition comb = head.addOrReplaceChild("comb", CubeListBuilder.create().texOffs(18, 4).addBox(0.0F, -9.0F, -2.0F, 0.0F, 6.0F, 5.0F), PartPose.ZERO);
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(32, 3).addBox(0.0F, -10.0F, 4.0F, 0.0F, 10.0F, 6.0F), PartPose.offset(0.0F, 16.0F, 0.0F));
        PartDefinition leg0 = partdefinition.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F), PartPose.offset(-2.0F, 19.0F, 1.0F));
        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F), PartPose.offset(1.0F, 19.0F, 1.0F));
        PartDefinition wing0 = partdefinition.addOrReplaceChild("wing0", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.offset(-3.0F, 13.0F, 0.0F));
        PartDefinition wing1 = partdefinition.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.offset(3.0F, 13.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(RenderChickens.ChickensRenderState state) {
        super.setupAnim(state);
        float f = (Mth.sin(state.flap) + 1.0F) * state.flapSpeed;
        this.head.xRot = state.xRot * 0.017453292F;
        this.head.yRot = state.yRot * 0.017453292F;
        float g = state.walkAnimationSpeed;
        float h = state.walkAnimationPos;
        this.leg1.xRot = Mth.cos(h * 0.6662F) * 1.4F * g;
        this.leg0.xRot = Mth.cos(h * 0.6662F + 3.1415927F) * 1.4F * g;
        this.wing1.zRot = f;
        this.wing0.zRot = -f;

        this.tail.visible = state.isRooster;
        this.comb.visible = state.isRooster;
        this.wattle.visible = !state.isRooster;
        this.wattleLarge.visible = state.isRooster;
    }
}
