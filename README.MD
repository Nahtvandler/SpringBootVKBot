

###Heroku
##Раскатка нового приложения
heroku login //логинимся
heroku apps:create vk-bot-vg //создаем новое приложение где vk-bot-vg название приложения в Heroku
git push heroku master //пушим мастер репозитория в мастер гита Heroku. После чего Heroku начинает раскатку.

#Пушим ветку репозитория отличную от мастера
git push heroku develop_v0.2:master

#Мониторим логи в реальном времени
heroku logs --tail



###Полезные ссылки
#Запуск Яндекс.Карт по URL
https://yandex.ru/dev/yandex-apps-launch/maps/doc/concepts/yandexmaps-web.html