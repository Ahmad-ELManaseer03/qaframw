from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time

options = webdriver.ChromeOptions()
options.add_argument('--headless')
driver = webdriver.Chrome(options=options)

try:
    driver.get('https://qc.care-connect.health/login')
    time.sleep(3)
    try:
        driver.switch_to.alert.accept()
        print('Alert accepted')
    except:
        print('No alert')
        
    driver.find_element(By.CSS_SELECTOR, 'input[formControlName=\"username\"]').send_keys('invalid@example.com')
    driver.find_element(By.CSS_SELECTOR, 'input[type=\"password\"]').send_keys('WrongPass123')
    driver.find_element(By.CSS_SELECTOR, 'button[type=\"submit\"].btn-login').click()
    
    print('Clicked login, waiting for error...')
    time.sleep(5)
    print('Body:', driver.find_element(By.TAG_NAME, 'body').text)
finally:
    driver.quit()
