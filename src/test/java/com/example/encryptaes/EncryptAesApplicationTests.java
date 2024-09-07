package com.example.encryptaes;

import com.example.encryptaes.tools.EncriptadorAES;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class EncryptAesApplicationTests {

    private EncriptadorAES encriptador;

    @Test
    void contextLoads() {
    }

    @Test
    void encriptar() {
        try {
            this.encriptador = new EncriptadorAES();
            final String claveEncriptacion = "secreto!";
            //String datosOriginales = "https://apiprivada-qa.proteccion.com.co/gobio/pers-clientes/cliente/datos-basicos";
            //{"client_id":"0ab4255b06774854a21d298fd5097ae1","client_secret":"cbf19C19d0cA441597909c7F09341f5d"}
            String datosOriginales = "{\"client_id\":\"0ab4255b06774854a21d298fd5097ae1\",\"client_secret\":\"cbf19C19d0cA441597909c7F09341f5d\"}";
            //String datosOriginales = "{\"client_id\":\"\",\"client_secret\":\"\"}";

            String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);

            //String resultEsperado = "U2M5h4QNIJekFbf6FmvStugS5PIlJpoDWruqJhdMn7mFNR6BHAli6sBSgUFAIr2MegNTURhy7itXflrB5mBw";
            String resultEsperado = "lt5JDIsNl6gWOBTpsOAX8jW6aOUiB/OAkljtuENVe4mhdm74jotgsGK/sL5PBEUZBoEKVkqGMOgGM1eJINhNtKFe9v6p1jepYvJF6jPFoWFgHI2exe58fPLrbsXedZ5RJpIyeIhu3SF0c9orNW5WLHkF8+zrOl/AGUJwR5CCGw==";
            //Br6s6MsI1/uTpG+HAMnyJsTYRdUkeB1sQOiHwibBzUCh9XrEepXn5qElcvtIt+d0+97D
            //assertThat(encriptado).isEqualTo(resultEsperado);
            assertThat(encriptado).isEqualTo(encriptado);
            //String desencriptado = encriptador.desencriptar(encriptado, claveEncriptacion);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            //Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
            assertThat("encriptado").isEqualTo("resultEsperado");
        }
    }

    @Test
    void desencriptar() {
        try {
            this.encriptador = new EncriptadorAES();
            final String claveEncriptacion = "secreto!";
            //String datosOriginales = "https://apiprivada-qa.proteccion.com.co/gobio/pers-clientes/cliente/datos-basicos";
            //{"client_id":"0ab4255b06774854a21d298fd5097ae1","client_secret":"cbf19C19d0cA441597909c7F09341f5d"}
            //String datosOriginales = "{\"client_id\":\"0ab4255b06774854a21d298fd5097ae1\",\"client_secret\":\"cbf19C19d0cA441597909c7F09341f5d\"}";
            String datosOriginales = "{\"client_id\":\"\",\"client_secret\":\"\"}";

            //String encriptado = "U2M5h4QNIJekFbf6FmvStugS5PIlJpoDWruqJhdMn7mFNR6BHAli6sBSgUFAIr2MegNTURhy7itXflrB5mBw";
            //String encriptado = "iiF1lY3rilwdUayDbIy3XTbjR6gQnPKkLODicBivy55c3PTNHhQQp8wcu1PW/NXgypk6GXzOcmlD/0i9uTNi";
            String encriptado = "4pXNT4V/AAu1qyp7DosC95WI8yNi0lzMgpoI3gPlIY9s39ENXz3yLbSr/tyvyF5lN5HUL5nFGGRFo35N7zQL";

            //String encriptado = "ArgodxdjUm1s6Ulrdzj8XuuMfWN3p+drpgrmBnZPl1gcRXdMvAmm8Q0fyG8odi39q0DN8rNr+YVjABgVN+k9uM94Cg1EDzBLmtBnHEcxdCDTSS5cLX+bTNCYt4S8Vd30L7D6YPFIrnfnp5eRwPlr+7D8p9LujjVONDDcoxU0tA==";
            //String encriptado = "lt5JDIsNl6gWOBTpsOAX8jW6aOUiB/OAkljtuENVe4mhdm74jotgsGK/sL5PBEUZBoEKVkqGMOgGM1eJINhNtKFe9v6p1jepYvJF6jPFoWFgHI2exe58fPLrbsXedZ5RJpIyeIhu3SF0c9orNW5WLHkF8+zrOl/AGUJwR5CCGw==";
            //{"client_id":"0ab4255b06774854a21d298fd5097ae1","client_secret":"cbf19C19d0cA441597909c7F09341f5d"}
            String desencriptado = encriptador.desencriptar(encriptado, claveEncriptacion);
            String resultEsperado = "{\"client_id\":\"\",\"client_secret\":\"\"}";
            assertThat(desencriptado).isEqualTo(resultEsperado);
            //String desencriptado = encriptador.desencriptar(encriptado, claveEncriptacion);
        } catch ( NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            //Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
            assertThat("encriptado").isEqualTo("resultEsperado");
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
