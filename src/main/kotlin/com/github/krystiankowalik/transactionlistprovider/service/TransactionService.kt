package com.github.krystiankowalik.transactionlistprovider.service

import com.github.krystiankowalik.transactionlistprovider.io.TransactionRepository
import com.github.krystiankowalik.transactionlistprovider.model.Transaction
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.temporal.ChronoUnit

@Service
@PropertySource(ignoreResourceNotFound=false,value= ["classpath:login.properties"])
class TransactionService(private val transactionRepository: TransactionRepository) {

    @Value("\${myuser.name}")
    private lateinit var userName: String

    @Value("\${myuser.pass}")
    private lateinit var userPass: String

    private fun getRecentTransactionsFromWebsite(): ArrayList<Transaction> {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver")

        val driver = ChromeDriver()
        driver.get("https://online.mbank.pl/pl/Login")

        val wait = WebDriverWait(driver, 20)
        wait.until<WebElement>({ d -> d.findElement(By.id("userID")) })

        driver.findElement(By.id("userID")).sendKeys(userName)
        driver.findElement(By.id("pass")).sendKeys(userPass)
        driver.findElement(By.id("submitButton")).click()

        wait.until<WebElement>({ d -> d.findElement(By.id("main")) })

        driver.get("https://online.mbank.pl/pl#/history")

        wait.until<WebElement>({ d -> d.findElement(By.id("search-phrase")) })
        wait.until<WebElement>({ d -> d.findElement(By.id("transactionListContainer")) })
        wait.until<WebElement>({ d -> d.findElement(By.tagName("li")) })
        Thread.sleep(5_000)

        val listElements = driver.findElements(By.tagName("li"))

        val transactions = ArrayList<Transaction>()
        listElements.forEach { i ->
            try {
                if (i.getAttribute("data-income") != null) {
                    transactions.add(Transaction(
                            id = i.getAttribute("data-id"),
                            amount = i.getAttribute("data-amount"),
                            date = i.getAttribute("data-timestamp"),
                            description = i.findElement(By.className("label")).getAttribute("data-original-title"),
                            currency = i.getAttribute("data-currency")
                    ))

                }
            } catch (e: Exception) {
                System.out.println("some exception " + e.message)
            }
        }
        println(transactions.size)

        driver.close()

        return transactions
    }

    @Scheduled(fixedRate = 3_600_000)
    private fun pullNewTransactionsToDb() {
        do {
            val transactions = getRecentTransactionsFromWebsite()
            transactionRepository.deleteAll()
            transactionRepository.saveAll(transactions)
        } while (transactions.isEmpty())


    }

    fun getRecentTransactions(fresh: Boolean): List<Transaction> {
        return if (fresh) {
            pullNewTransactionsToDb()
            getRecentTransactionsFromDb()
        } else {
            getRecentTransactionsFromDb()
        }
    }

    private fun getRecentTransactionsFromDb(): List<Transaction> {
        return transactionRepository.findAll().toList()
    }

}