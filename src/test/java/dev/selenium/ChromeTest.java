package dev.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeTest {

    /* ChromeDriver abre una ventana de Chrome */
    ChromeDriver driver;
    
    /* Pruebas de junit: */
    @Test
    public void test() throws InterruptedException{
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        Thread.sleep(2000); /* Acepta longs enteros en milisegundos */
        System.out.println(driver.getTitle());
        driver.findElement(By.id("my-text-id"));
        /* Lo mejor sería buscar por id, ya que es un elemento
         * que no se puede repetir. Si buscamos por la etiqueta name y nos salen varios
         * resultados al ser .findElement y no elements(en plural) daría error*/
        WebElement textInput = driver.findElement(By.id("my-text-id"));
        textInput.sendKeys("carolina");
        /* Va a pasar valores de teclas */
        Thread.sleep(1500);
        WebElement botonSubmit = driver.findElement(By.className("btn-outline-primary"));
        /* className solo permite buscar una clase a la vez */
        botonSubmit.click();
        WebElement submitted = driver.findElement(By.className("display-6"));
        System.out.println(submitted.getText());
        /* Con esto hacemos una comprobación de que lo que obtuvimos es lo que esperábamos  */
        assertEquals("Form submitted", submitted.getText());
        Thread.sleep(1000); 
        driver.quit();

    }

    @Test
    public void elementForms () throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        Thread.sleep(2000);
        WebElement check = driver.findElement(By.id("my-check-1"));
        check.click();
        Thread.sleep(2000);
        assertTrue(check.isSelected());
        //assertFalse(check.isSelected()); así saldría la prueba verificada porque ya viene marcado por defecto en la pag
        driver.quit();
    }

    @Test
    public void disabledInput () throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        Thread.sleep(2000);
        WebElement disabledInput = driver.findElement(By.name("my-disabled"));
        disabledInput.sendKeys("carolina");
        driver.quit();
        
    }

    @Test
    public void elementUploadForms() throws Exception {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.get("https://the-internet.herokuapp.com/upload");
        /* Declaramos una variable String con el nombre de la imagen que queremos subir para
         * que si en el futuro queremos cambiar de imagen solo tegamos que cambiar su nombre
         * y no la ruta relativa entera, el assertEquals... */
        String nameImage = "medusa.png";
        File uploadFile = new File("src/test/files/"+nameImage);
        WebElement fileInput = driver.findElement(By.cssSelector("input[type=file]"));
        fileInput.sendKeys(uploadFile.getAbsolutePath());
        Thread.sleep(1000);
        /* No se pone directamente la ruta absoluta porque sería única para este ordenador,
         * si quisiesemos hacer la prueba en otro ordenador dará problemas*/
        
         driver.findElement(By.id("file-submit")).click();
        /* Sería lo mismo que lo de abajo pero sin declarar una variable que no vamos a usar
        WebElement buttomUpload = driver.findElement(By.id("file-submit"));
        buttomUpload.click(); */
        Thread.sleep(2000);
        //driver.quit();
        WebElement fileUpload = driver.findElement(By.id("uploaded-files"));
        assertEquals(nameImage, fileUpload.getText());
        /* Al poner como lo esperado el nameImage y no el nombre de la imagen escrito a mano evitamos
         * "Literales", que a futuro complican las pruebas */
    }
    
}