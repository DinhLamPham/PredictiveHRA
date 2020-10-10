from django import forms


class UploadFileForm(forms.Form):
    name = forms.CharField(max_length=30,
                           # help_text='input name',
                           required=True
                           )
    email = forms.EmailField(max_length=254,
                             required=True,
                             widget=forms.EmailInput(
                                 attrs={

                                 }
                             ))
    message = forms.CharField(max_length=2000,
                              required=True,
                              widget=forms.Textarea(attrs={}),
                              help_text='Write here your message')
    myfile = forms.FileField()



    def clean(self):
        cleaned_data = super(UploadFileForm, self).clean()
        name = cleaned_data.get('name')

        message = cleaned_data.get('message')
        if not name and not message:
            raise forms.ValidationError('You have to write something')
