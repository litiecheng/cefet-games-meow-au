package br.cefetmg.games.transition;

import br.cefetmg.games.screens.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Um efeito de transição que trabalha com um valor de transparência da tela.
 * Esta é uma classe abstrata que possui implementações concretas em
 * {@link FadeInTransitionEffect} e {@link FadeOutTransitionEffect}.
 * 
 * @author Estevão e Sarah
 */
public abstract class FadeTransitionEffect extends TransitionEffect {

    private final ShapeRenderer shapeRenderer;
    private final Color color;
    protected float alpha;

    protected FadeTransitionEffect(float duration, float initialAlpha) {
        super(duration);
        this.alpha = initialAlpha;
        this.color = new Color(0f, 0f, 0f, 0f);
        this.shapeRenderer = new ShapeRenderer(20);
    }

    /**
     * As classes filhas devem implementar uma política de atualização do
     * valor de alpha (ie., crescendo até 1 ou descrescendo até 0).
     */
    protected abstract void updateAlphaValue();

    @Override
    public void render(BaseScreen current) {
        current.render(delta);

        color.a = alpha;
        updateAlphaValue();
        drawFade(current.viewport);

        if (timeElapsed >= duration) {
            isFinished = true;
        }
    }

    private void drawFade(Viewport viewport) {
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

}
