package MetodosAuxiliares;

import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Recursos {
	
	private WebDriver driver;
	
	public Recursos(WebDriver driver) {
		this.driver = driver;
	}

	
	/**
	 * Método para mover mouse sobre um elemento e executar o drop-down
	 */
	public Consumer<By> hover = (By by) -> {
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(by)).perform();
	};
	
	/**
	 * Método para aguardar o elemento estar visível na DOM
	 * @param componente
	 */
	public void aguardarEstarPresente(By componente) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(componente));
	}
	
	/**
	 * Método para fazer scroll para o elemento passado por parâmetro
	 * @param elemento
	 */
	public void scrollParaElemento(By elemento) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement((elemento)));
		((JavascriptExecutor) driver).executeScript("scrollTo(0, 800)");
	}
	
	/**
	 * Método para retornar o elemento passando a data mais antiga por parâmetro
	 * @param data
	 * @return String - texto do elemento
	 * 	
	 */
	public String retornarArquivoMaisAntigo(String data) {
		return driver.findElement(By.xpath("//time-ago[@datetime='" + data + "']//preceding::td[2]/span/a")).getText();
	}
	
	public void clicarNoElemento(By elemento) {
		driver.findElement(elemento).click();
	}

}
