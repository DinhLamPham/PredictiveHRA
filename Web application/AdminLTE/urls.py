from django.contrib import admin
from django.urls import path
from . import views


app_name = 'AdminLte'
urlpatterns = [
    path('', views.home, name='home'),
    # path('uploadlog', views.UploadLog.as_view(), name='uploadlog'),
    path('uploadlog', views.upload_log, name='uploadlog'),
    path('viewloglist', views.view_xes_log_list, name='viewloglist'),
    path(r'preprocess_log', views.preprocess_log, name='preprocess_log'),

    path('visualizelog', views.visualize_log, name='viewloglist'),

]
