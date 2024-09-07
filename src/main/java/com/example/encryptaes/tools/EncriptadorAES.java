package com.example.encryptaes.tools;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


public class EncriptadorAES {

    /**
     * Crea la clave de encriptacion usada internamente
     * @param clave Clave que se usara para encriptar
     * @return Clave de encriptacion
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private SecretKeySpec crearClave(String clave) throws NoSuchAlgorithmException {
        byte[] claveEncriptacion = clave.getBytes(StandardCharsets.UTF_8);

        MessageDigest sha = MessageDigest.getInstance("SHA-1");

        claveEncriptacion = sha.digest(claveEncriptacion);
        claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);

        return new SecretKeySpec(claveEncriptacion, "AES");
    }

    /**
     * Aplica la encriptacion AES a la cadena de texto usando la clave indicada
     * @param datos Cadena a encriptar
     * @param claveSecreta Clave para encriptar
     * @return Información encriptada
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String encriptar(String datos, String claveSecreta) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);

        /*
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        //Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] datosEncriptar = datos.getBytes(StandardCharsets.UTF_8);
        byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
        return Base64.getEncoder().encodeToString(bytesEncriptados);
         */
        byte[] clearTextbytes = datos.getBytes(StandardCharsets.UTF_8);
        //final SecretKey secretKey = crearClave("secreto!");
        final Cipher AES_cipherInstance = Cipher.getInstance("AES/GCM/NoPadding");
        AES_cipherInstance.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedText = AES_cipherInstance.doFinal(clearTextbytes);

        byte[] iv = AES_cipherInstance.getIV();
        byte[] message = new byte[12 + clearTextbytes.length + 16];
        System.arraycopy(iv, 0, message, 0, 12);
        System.arraycopy(encryptedText, 0, message, 12, encryptedText.length);
        return Base64.getEncoder().encodeToString(message);
    }

    /**
     * Desencripta la cadena de texto indicada usando la clave de encriptacion
     * @param datosEncriptados Datos encriptados
     * @param claveSecreta Clave de encriptacion
     * @return Informacion desencriptada
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String desencriptar(String datosEncriptados, String claveSecreta) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        /*
        SecretKeySpec secretKey = this.crearClave(claveSecreta);

        //Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        //GCMParameterSpec params = new GCMParameterSpec(128, encryptedText, 0, 12);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        return new String(datosDesencriptados);
        */
        SecretKeySpec secretKey = this.crearClave(claveSecreta);
        byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
        //Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec params = new GCMParameterSpec(128, bytesEncriptados, 0, 12);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, params);

        //byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
        //byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        //byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados, 12, bytesEncriptados.length - 12);
        return new String(datosDesencriptados, StandardCharsets.UTF_8);

        /*
        final Cipher AES_cipherInstance = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec params = new GCMParameterSpec(128, encryptedText, 0, 12);
        AES_cipherInstance.init(Cipher.DECRYPT_MODE, secretKey, params);
        byte[] decryptedText = AES_cipherInstance.doFinal(encryptedText, 12, encryptedText.length - 12);
        return new String(decryptedText, "UTF-8");
         */
    }
}
