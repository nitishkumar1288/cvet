package com.fluentcommerce.test;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.test.mocking.scene.SceneBuilder;
import com.fluentretail.api.v2.client.ReadOnlyFluentApiClient;
import com.fluentretail.rubix.v2.action.ActionFactory;
import com.fluentretail.rubix.v2.context.Context;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseTest {
    protected ContextWrapper context;
    protected SceneBuilder sceneBuilder;

    @Before
    public void baseSetup() {
        Context rubixContext = mock(Context.class);
        context = ContextWrapper.from(rubixContext);
        sceneBuilder = new SceneBuilder();

        ActionFactory actionFactory = mock(ActionFactory.class);
        when(context.action()).thenReturn(actionFactory);

        ReadOnlyFluentApiClient api = mock(ReadOnlyFluentApiClient.class);
        when(context.api()).thenReturn(api);
    }

    @Test
    public void testValues() {
        String testActualValue = "";
        String testExpectedValue = "";
        assertEquals(testExpectedValue,testActualValue);
    }
}
