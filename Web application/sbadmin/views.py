from django.core.files.storage import FileSystemStorage
from django.shortcuts import render

# Create your views here.
from django.views import generic
# from . import core
from sbadmin.forms import UploadFileForm


def home(request):

    if request.method == 'POST':
        form = UploadFileForm(request.POST)
        print("name: ----------------", request.POST.get('name'))
        print("email: ----------------", request.POST.get('email'))
        print("message: ----------------", request.POST.get('message'))

        myfile = request.FILES['myfile']
        fs = FileSystemStorage()
        filename = fs.save(myfile.name, myfile)
        uploaded_file_url = fs.url(filename)

        print("file name: ", myfile.name)
        print("file size ", myfile.size)


        if form.is_valid():
            print('form is valid')
            pass  # does nothing, just trigger the validation
        else:
            print('form is not valid')
    else:
        print("form is not valid. rendering new form...")
        form = UploadFileForm()
    return render(request, 'sbadmin/home.html', {'form': form})



class IndexView(generic.ListView):
    template_name = 'sbadmin/index.html'

    def get_queryset(self):
        pass


class TableView(generic.ListView):
    template_name = 'sbadmin/table.html'
    context_object_name = ''

    def get_queryset(self):
        pass


class ChartView(generic.ListView):
    template_name = 'sbadmin/chart.html'
    context_object_name = ''

    def get_queryset(self):
        pass
