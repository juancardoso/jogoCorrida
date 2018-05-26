package screen;

import com.jme3.app.SimpleApplication;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.StyleBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.examples.controls.common.CommonBuilders;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.Color;

/**
 * Nifty GUI 1.3 demo using Java. Based on
 * /nifty-default-controls-examples/trunk/src/main/java/de/lessvoid/nifty/examples/.
 *
 * @author zathras
 */
public class ManagerUI extends SimpleApplication {

    public static Screen createIntroScreen(final Nifty nifty) {
        Screen screen = new ScreenBuilder("start") {

            {
                controller(new DefaultScreenController() {

                    @Override
                    public void onStartScreen() {
                        nifty.gotoScreen("demo");
                    }
                });
                layer(new LayerBuilder("layer") {

                    {
                        childLayoutCenter();
                        onStartScreenEffect(new EffectBuilder("fade") {

                            {
                                length(3000);
                                effectParameter("start", "#0");
                                effectParameter("end", "#f");
                            }
                        });
                        onStartScreenEffect(new EffectBuilder("playSound") {

                            {
                                startDelay(1400);
                                effectParameter("sound", "intro");
                            }
                        });
                        onActiveEffect(new EffectBuilder("gradient") {

                            {
                                effectValue("offset", "0%", "color", "#66666fff");
                                effectValue("offset", "85%", "color", "#000f");
                                effectValue("offset", "100%", "color", "#44444fff");
                            }
                        });
                        panel(new PanelBuilder() {

                            {
                                alignCenter();
                                valignCenter();
                                childLayoutHorizontal();
                                width("856px");
                                panel(new PanelBuilder() {

                                    {
                                        width("300px");
                                        height("256px");
                                        childLayoutCenter();
                                        text(new TextBuilder() {

                                            {
                                                text("Car Run");
                                                font("Interface/verdana-48-regular.fnt");
                                                color("#8fff");
                                                style("base-font");
                                                alignCenter();
                                                valignCenter();
                                                onStartScreenEffect(new EffectBuilder("fade") {

                                                    {
                                                        length(1000);
                                                        effectValue("time", "1700", "value", "0.0");
                                                        effectValue("time", "2000", "value", "1.0");
                                                        effectValue("time", "2600", "value", "1.0");
                                                        effectValue("time", "3200", "value", "0.0");
                                                        post(false);
                                                        neverStopRendering(true);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                                panel(new PanelBuilder() {

                                    {
                                        alignCenter();
                                        valignCenter();
                                        childLayoutOverlay();
                                        width("256px");
                                        height("256px");
                                        onStartScreenEffect(new EffectBuilder("shake") {

                                            {
                                                length(250);
                                                startDelay(1300);
                                                inherit();
                                                effectParameter("global", "false");
                                                effectParameter("distance", "10.");
                                            }
                                        });
                                        onStartScreenEffect(new EffectBuilder("imageSize") {

                                            {
                                                length(600);
                                                startDelay(3000);
                                                effectParameter("startSize", "1.0");
                                                effectParameter("endSize", "2.0");
                                                inherit();
                                                neverStopRendering(true);
                                            }
                                        });
                                        onStartScreenEffect(new EffectBuilder("fade") {

                                            {
                                                length(600);
                                                startDelay(3000);
                                                effectParameter("start", "#f");
                                                effectParameter("end", "#0");
                                                inherit();
                                                neverStopRendering(true);
                                            }
                                        });
                                        image(new ImageBuilder() {

                                            {
                                                filename("Interface/yin.png");
                                                onStartScreenEffect(new EffectBuilder("move") {
                                                    {
                                                        length(1000);
                                                        startDelay(300);
                                                        timeType("exp");
                                                        effectParameter("factor", "6.f");
                                                        effectParameter("mode", "in");
                                                        effectParameter("direction", "left");
                                                    }
                                                });
                                            }
                                        });
                                        image(new ImageBuilder() {

                                            {
                                                filename("Interface/yang.png");
                                                onStartScreenEffect(new EffectBuilder("move") {

                                                    {
                                                        length(1000);
                                                        startDelay(300);
                                                        timeType("exp");
                                                        effectParameter("factor", "6.f");
                                                        effectParameter("mode", "in");
                                                        effectParameter("direction", "right");
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                                panel(new PanelBuilder() {

                                    {
                                        width("300px");
                                        height("256px");
                                        childLayoutCenter();
                                        text(new TextBuilder() {

                                            {
                                                text("On The Run");
                                                font("Interface/verdana-48-regular.fnt");
                                                color("#8fff");
                                                style("base-font");
                                                alignCenter();
                                                valignCenter();
                                                onStartScreenEffect(new EffectBuilder("fade") {

                                                    {
                                                        length(1000);
                                                        effectValue("time", "1700", "value", "0.0");
                                                        effectValue("time", "2000", "value", "1.0");
                                                        effectValue("time", "2600", "value", "1.0");
                                                        effectValue("time", "3200", "value", "0.0");
                                                        post(false);
                                                        neverStopRendering(true);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
                layer(new LayerBuilder() {

                    {
                        backgroundColor("#ddff");
                        onStartScreenEffect(new EffectBuilder("fade") {

                            {
                                length(1000);
                                startDelay(3000);
                                effectParameter("start", "#0");
                                effectParameter("end", "#f");
                            }
                        });
                    }
                });
            }
        }.build(nifty);
        return screen;
    }

    public static Screen createGameScreen(final Nifty nifty) {
        final CommonBuilders common = new CommonBuilders();
        Screen screen = new ScreenBuilder("demo") {

            {
                layer(new LayerBuilder() {

                    {
                        childLayoutVertical();
                        panel(new PanelBuilder() {

                            {
                                height("*");
                            }
                        });
                        panel(new PanelBuilder() {

                            {
                                childLayoutCenter();
                                height("80px");
                                width("100%");
                                backgroundColor("#5788");
                                panel(new PanelBuilder() {

                                    {
                                        paddingLeft("25px");
                                        paddingRight("25px");
                                        height("40%");
                                        width("100%");
                                        alignCenter();
                                        valignCenter();
                                        childLayoutHorizontal();
                                        control(new LabelBuilder() {

                                            {
                                                label("Laps: ");
                                                font("Interface/verdana-48-regular.fnt");
                                                color("#8fff");
                                            }
                                        });
                                        panel(common.hspacer("7px"));
                                        control(new LabelBuilder() {

                                            {
                                                label(" 0 ");
                                                font("Interface/verdana-48-regular.fnt");
                                                color("#8fff");
                                            }
                                        });
                                        panel(common.hspacer("*"));
                                        control(new ButtonBuilder("resetScreenButton", "New Game") {

                                            {
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
                layer(new LayerBuilder("whiteOverlay") {

                    {
                        onCustomEffect(new EffectBuilder("renderQuad") {

                            {
                                customKey("onResolutionStart");
                                length(350);
                                neverStopRendering(false);
                            }
                        });
                        onStartScreenEffect(new EffectBuilder("renderQuad") {

                            {
                                length(300);
                                effectParameter("startColor", "#ddff");
                                effectParameter("endColor", "#0000");
                            }
                        });
                        onEndScreenEffect(new EffectBuilder("renderQuad") {

                            {
                                length(300);
                                effectParameter("startColor", "#0000");
                                effectParameter("endColor", "#ddff");
                            }
                        });
                    }
                });
            }
        }.build(nifty);
        return screen;
    }

    public static void registerMenuButtonHintStyle(final Nifty nifty) {
        new StyleBuilder() {

            {
                id("special-hint");
                base("nifty-panel-bright");
                childLayoutCenter();
                onShowEffect(new EffectBuilder("fade") {

                    {
                        length(150);
                        effectParameter("start", "#0");
                        effectParameter("end", "#d");
                        inherit();
                        neverStopRendering(true);
                    }
                });
                onShowEffect(new EffectBuilder("move") {

                    {
                        length(150);
                        inherit();
                        neverStopRendering(true);
                        effectParameter("mode", "fromOffset");
                        effectParameter("offsetY", "-15");
                    }
                });
                onCustomEffect(new EffectBuilder("fade") {

                    {
                        length(150);
                        effectParameter("start", "#d");
                        effectParameter("end", "#0");
                        inherit();
                        neverStopRendering(true);
                    }
                });
                onCustomEffect(new EffectBuilder("move") {

                    {
                        length(150);
                        inherit();
                        neverStopRendering(true);
                        effectParameter("mode", "toOffset");
                        effectParameter("offsetY", "-15");
                    }
                });
            }
        }.build(nifty);

        new StyleBuilder() {

            {
                id("special-hint#hint-text");
                base("base-font");
                alignLeft();
                valignCenter();
                textHAlignLeft();
                color(new Color("#000f"));
            }
        }.build(nifty);
    }

    public static void registerStyles(final Nifty nifty) {
        new StyleBuilder() {

            {
                id("base-font-link");
                base("base-font");
                color("#8fff");
                interactOnRelease("$action");
                onHoverEffect(new HoverEffectBuilder("changeMouseCursor") {

                    {
                        effectParameter("id", "hand");
                    }
                });
            }
        }.build(nifty);

        new StyleBuilder() {

            {
                id("creditsImage");
                alignCenter();
            }
        }.build(nifty);

        new StyleBuilder() {

            {
                id("creditsCaption");
                font("Interface/verdana-48-regular.fnt");
                width("100%");
                textHAlignCenter();
            }
        }.build(nifty);

        new StyleBuilder() {

            {
                id("creditsCenter");
                base("base-font");
                width("100%");
                textHAlignCenter();
            }
        }.build(nifty);
    }

    @Override
    public void simpleInitApp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
