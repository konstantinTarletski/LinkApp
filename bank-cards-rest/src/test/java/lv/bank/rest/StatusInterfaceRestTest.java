package lv.bank.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatusInterfaceRestTest {

    @InjectMocks
    private StatusInterfaceRest rest;

    @Mock
    private StatusService service;

    @Test
    public void liveness() {
        Response response = rest.liveness();

        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    public void version() {
        String version = "1.0.0";
        when(service.getApplicationVersion()).thenReturn(version);

        Response response = rest.getVersion();

        assertEquals(200, response.getStatus());
        assertEquals(response.getEntity(), version);
    }

}
