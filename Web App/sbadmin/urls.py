from django.contrib import admin
from django.urls import path
from . import views


app_name = 'sbadmin'
urlpatterns = [
    path('table/', views.TableView.as_view(), name='table'),
    path('chart/', views.ChartView.as_view(), name='chart'),
    path('', views.IndexView.as_view(), name='index'),
    path('home/', views.home, name='home')
]
