Создано два тестовых класса. Для проверки методов для Product - ProductTest, для Category - CategoryTest.

----------------------------------------------------------

В ProductTest проверяется 5 запросов:

get         /api/v1/products

post        /api/v1/products

put         /api/v1/products

get         /api/v1/products/{id}

delete      /api/v1/products/{id}

Тесты обязательно запускать по порядку

----------------------------------------------------------

В CategoryTest проверяется 1 запрос:
get         /api/v1/categories/{id}
