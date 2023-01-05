package ru.liner.facerapp.engine.script.dependency;

import android.graphics.Typeface;

import ru.liner.facerapp.engine.async.Operation;
import ru.liner.facerapp.engine.resource.Resource;
import ru.liner.facerapp.engine.scenegraph.dependency.AsyncResolutionDependency;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class LegacyAsyncTypefaceDependecy extends AsyncResolutionDependency<Typeface> {
    public LegacyAsyncTypefaceDependecy(Resource<Typeface> typefaceResource) {
        super(new UpdateOperation(typefaceResource));
    }

    /* loaded from: classes.dex */
    protected static class UpdateOperation implements Operation<Long, Typeface> {
        private final Resource<Typeface> typefaceResource;

        public UpdateOperation(Resource<Typeface> typefaceResource) {
            this.typefaceResource = typefaceResource;
        }

        public Typeface execute(Long input) throws Exception {
            if (this.typefaceResource != null) {
                return this.typefaceResource.resolve();
            }
            return null;
        }
    }
}