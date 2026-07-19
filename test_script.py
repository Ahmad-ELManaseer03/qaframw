from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time

options = webdriver.ChromeOptions()
options.add_argument('--headless')
options.add_argument('--guest')
driver = webdriver.Chrome(options=options)

try:
    driver.get('https://qc.care-connect.health/login')
    
    WebDriverWait(driver, 15).until(EC.visibility_of_element_located((By.CSS_SELECTOR, 'input[formControlName=\"username\"]')))
    driver.find_element(By.CSS_SELECTOR, 'input[formControlName=\"username\"]').send_keys('invalid@example.com')
    driver.find_element(By.CSS_SELECTOR, 'input[type=\"password\"]').send_keys('WrongPass123')
    driver.find_element(By.CSS_SELECTOR, 'button[type=\"submit\"].btn-login').click()
    
    print('Clicked login, waiting for error...')
    for i in range(20):
        time.sleep(1)
        print('Tick ' + str(i))
        elems = driver.find_elements(By.XPATH, '//*[contains(translate(text(), \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\", \"abcdefghijklmnopqrstuvwxyz\"), \"invalid\")]')
        if elems:
            for e in elems:
                print('Found error text in tag:', e.tag_name, 'class:', e.get_attribute('class'))
            break
        
        elems2 = driver.find_elements(By.CSS_SELECTOR, 'p-toast-detail')
        for e in elems2:
            print('p-toast-detail found with text: ', e.text)
finally:
    driver.quit()
