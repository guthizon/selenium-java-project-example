import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import MetodosAuxiliares.Recursos;

public class DesafioTestes {
	private WebDriver driver;
	private WebElement elemento;
	private Recursos recursos;

	@BeforeClass
	public void beforeClass() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test(testName = "Desafio 1", priority = 0)
	public void verificarSeContemTexto() {
		recursos = new Recursos(driver);
		driver.get("http://saucelabs.com/");

		By menuSolutions = By.xpath("//*[@id=\"site-header\"]/div[3]/div/div/div[2]/div[1]/span");
		By menuCrossBrowser = By.linkText("Cross Browser Testing");
		By acordeonAberto = By.xpath("//div[@class='y2tJ' and contains(@style,'translateY(0%)')]");
		By lerMais = By.xpath("//a[@href='/products/open-source-frameworks/selenium']");
		By textoValidacao = By.xpath("//p[contains(text(),'Run Selenium tests in the cloud')]");
		By textoInexistente = By.xpath("//p[contains(text(),'Run Selenium tests securely in the cloud')]");

		recursos.aguardarEstarPresente(menuSolutions);
		recursos.clicarNoElemento(menuSolutions);
		recursos.aguardarEstarPresente(acordeonAberto);
		recursos.aguardarEstarPresente(menuCrossBrowser);
		recursos.clicarNoElemento(menuCrossBrowser);
		recursos.aguardarEstarPresente(lerMais);
		recursos.scrollParaElemento(lerMais);
		elemento = driver.findElement(lerMais);
		elemento.click();
		recursos.clicarNoElemento(lerMais);
		recursos.aguardarEstarPresente(textoValidacao);
		elemento = driver.findElement(textoValidacao);

		Assert.assertTrue(elemento.isDisplayed());
		Assert.assertTrue(driver.findElements(textoInexistente).isEmpty());
	}

	@Test(testName = "Desafio 2", priority = 1, enabled = true)
	public void compararDatas() throws ParseException {

		driver.get("https://github.com/");

		By campoPesquisar = By.xpath("//input[@name='q']");
		By linkSeleniumHq = By.xpath("//a[@href='/SeleniumHQ/selenium']");
		By paginaBraches = By.xpath("//span[contains(text(),' browser automation framework and ecosystem')]");

		recursos.aguardarEstarPresente(campoPesquisar);
		elemento = driver.findElement(campoPesquisar);
		elemento.sendKeys("Selenium");
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).perform();
		recursos.aguardarEstarPresente(linkSeleniumHq);
		recursos.clicarNoElemento(linkSeleniumHq);
		recursos.aguardarEstarPresente(paginaBraches);

		List<WebElement> elementos = driver.findElements(By.tagName("time-ago"));
		Date dataAtual = null;
		String xpathData = "";

		for (WebElement el : elementos) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String data = el.getAttribute("datetime");

			String dataFormatada = data.replaceAll("T", " ");
			dataFormatada = dataFormatada.replaceAll("Z", "");
			Date date = sdf.parse(dataFormatada);

			if (dataAtual == null || date.compareTo(dataAtual) < 0) {
				dataAtual = date;
				xpathData = data;
			}
		}
		recursos.retornarArquivoMaisAntigo(xpathData);
		System.out.println("Arquivo mais antigo >> " + recursos.retornarArquivoMaisAntigo(xpathData) + " | Data do commit >> " + dataAtual);
	}

	@Test(testName = "Desafio 3", priority = 2)
	public void validarVersaoPro() {
		driver.get("http://extentreports.com/");

		recursos.hover.accept(By.linkText("DOCS"));
		recursos.hover.accept(By.linkText("Version 3"));
		recursos.hover.accept(By.linkText("Java"));
		driver.findElement(By.linkText("Java")).click();

		By paginaJavaDoc = By.id("javadoc");
		recursos.aguardarEstarPresente(paginaJavaDoc);

		elemento = driver
				.findElement(By.xpath("//a[contains(text(),'Offline report')]//following::td[2]/i[@class='fa fa-check text-success']"));
		Assert.assertTrue(elemento.isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
