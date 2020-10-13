from django import forms


class UploadFileForm(forms.Form):
    logname = forms.CharField(max_length=30,
                              # help_text='input name',
                              required=True
                              )
    description = forms.CharField(max_length=2000,
                              required=False,
                              widget=forms.Textarea(attrs={})
                              )
    upload_file = forms.FileField()



    def clean(self):
        cleaned_data = super(UploadFileForm, self).clean()


