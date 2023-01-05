package ru.liner.facerapp.engine.factory;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.render.ColorNode;
import ru.liner.facerapp.engine.scenegraph.node.render.GlowNode;
import ru.liner.facerapp.engine.scenegraph.node.render.StyleNode;
import ru.liner.facerapp.engine.scenegraph.node.render.TextNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyTextNode;

/* loaded from: classes.dex */
public class TextBuilder extends NodeBuilder {
    private float size;
    private Dependency<Float> sizeDependency;
    private String text;
    private Dependency<String> textDependency;
    private Typeface typeface;
    private Dependency<Typeface> typefaceDependency;
    private Paint.Align alignment = Paint.Align.LEFT;
    private ColorNode colorNode = null;
    private GlowNode glowNode = null;
    private StyleNode styleNode = null;

    public TextBuilder(String text, Typeface typeface, float size) {
        this.text = text;
        this.typeface = typeface;
        this.size = size;
    }

    public TextBuilder(Dependency<String> textDependency, Dependency<Typeface> typefaceDependency, Dependency<Float> sizeDependency) {
        this.textDependency = textDependency;
        this.typefaceDependency = typefaceDependency;
        this.sizeDependency = sizeDependency;
    }

    public TextBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public TextBuilder setTypefaceDependency(Dependency<Typeface> dependency) {
        this.typefaceDependency = dependency;
        return this;
    }

    public TextBuilder setTextDependency(Dependency<String> textDependency) {
        this.textDependency = textDependency;
        return this;
    }

    public TextBuilder setAlignment(Paint.Align alignment) {
        this.alignment = alignment;
        return this;
    }

    public TextBuilder setSize(float size) {
        this.size = size;
        return this;
    }

    public TextBuilder setSizeDependency(Dependency<Float> sizeDependency) {
        this.sizeDependency = sizeDependency;
        return this;
    }

    public TextBuilder setTypeface(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public TextBuilder setColorNode(ColorNode colorNode) {
        this.colorNode = colorNode;
        return this;
    }

    public TextBuilder setGlowNode(GlowNode glowNode) {
        this.glowNode = glowNode;
        return this;
    }

    public TextBuilder setStyleNode(StyleNode styleNode) {
        this.styleNode = styleNode;
        return this;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.factory.NodeBuilder
    protected boolean buildNodes() {
        if (this.textDependency == null && (this.text == null || "".equals(this.text.trim()))) {
            return false;
        }
        if (this.colorNode == null) {
            this.colorNode = new ColorNode(Color.WHITE);
        }
        attach(this.colorNode);
        attach(this.glowNode);
        attach(this.styleNode);
        TextNode textNode = new DependencyTextNode(textDependency, typefaceDependency, sizeDependency, alignment);
        textNode.setText(text == null ? "---" : text);
        if(typeface != null)
            textNode.setTypeface(typeface);
        textNode.setSize(size);
        applyDependencies(textNode);
        attach(textNode);
        return true;
    }

    protected TextNode buildTextNode() {
        TextNode textNode = new DependencyTextNode(this.textDependency, this.typefaceDependency, this.sizeDependency, this.alignment);
        if (this.text != null) {
            textNode.setText(this.text);
        }
        if (this.typeface != null) {
            textNode.setTypeface(this.typeface);
        }
        textNode.setSize(this.size);
        applyDependencies(textNode);
        return textNode;
    }
}
