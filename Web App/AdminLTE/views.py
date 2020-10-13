from datetime import datetime

import pandas as pd
from django.core.files.storage import FileSystemStorage
from django.http import Http404
from django.shortcuts import render, redirect
from AdminLTE.Helper.MySQL.Query import InsertToLogFileTbl

# Create your views here.
from django.views import generic

from AdminLTE.Helper.MySQL.QueryAction import get_log_list, get_converted_list, get_trace_info_list, get_traceid_detail
from AdminLTE.Helper.MySQL.mySQLSettings import tbl_convertedlog_name, RunSQL
from AdminLTE.Helper.XES.GetTracesFromTxt import GetSingleTraceInfo, GetValueFromTrace, GetMultipleTraceInfo, \
    GenerateJsonFromInputTraces
from AdminLTE.Helper.XES.XESConvert import XESLogProcess
from AdminLTE.forms import UploadFileForm
from myproject.settings import MEDIA_ROOT


def home(request):
    return render(request, 'adminlte/index.html', {'tags': 'home'})


def upload_log(request):
    if request.method == 'POST':
        form = UploadFileForm(request.POST)
        myfile = request.FILES['upload_file']
        fs = FileSystemStorage()
        filename = fs.save(myfile.name, myfile)
        log_url = fs.url(filename)
        log_name = request.POST.get('logname')
        log_description = request.POST.get('discription')
        if not log_description:
            log_description = '...'
        log_size = str(round(myfile.size / 1000000, 2))
        log_id = log_url.split('/')[-1]
        log_date = str(datetime.now())
        InsertToLogFileTbl(log_id, log_name, log_description,
                           log_url, log_size, log_date)
        print('Insert log to system was successful!')
    else:
        print("form is not valid. rendering new form...")
        form = UploadFileForm()
    return render(request, 'adminlte/uploadlog.html', {'form': form, 'tags': 'uploadlog'})


def view_xes_log_list(request):
    df_loglist = get_log_list()

    # print('Count rows: ', len(df_loglist), 'Count columns: ', len(df_loglist.columns))
    # print('columns list: ', list(df_loglist))
    col_data = list(df_loglist)
    row_data = list(df_loglist.values.tolist())

    context = {'tags': 'view_xes_log',
               'df_loglist': df_loglist,
               'total_row': len(row_data),
               'total_col': range(len(col_data)),
               'row_data': row_data,
               'col_data': col_data
               }
    return render(request, 'adminlte/view_xes_log.html', context)


def preprocess_log(request):
    try:
        log_id = request.GET['log_id']
    except:
        log_id = None
    print('log_id: ', log_id)
    if log_id is not None:
        XESLogProcess(log_id, MEDIA_ROOT + "\\" + log_id, MEDIA_ROOT + "\\" + log_id.replace('.xes', '.txt'))

        sql = "UPDATE " + tbl_convertedlog_name + " SET Status = '1' WHERE ID =" + "'" + log_id + "'"
        RunSQL(sql)

    df_loglist = get_converted_list()

    col_data = list(df_loglist)
    row_data = list(df_loglist.values.tolist())

    context = {'tags': 'preprocess_log',
               'df_loglist': df_loglist,
               'total_row': len(row_data),
               'total_col': range(len(col_data)),
               'row_data': row_data,
               'col_data': col_data
               }
    return render(request, 'adminlte/preprocess_log.html', context)


def visualize_log(request):
    try:
        log_id = request.GET['log_id']
    except:
        log_id = None
    try:
        trace_id, visual_type = request.GET['trace_id'], request.GET['visual_type']
    except:
        trace_id, visual_type = None, None


    # Get list of trace here and transfer to context
    if log_id is not None:
        df_trace_list = get_trace_info_list(log_id)
        row_data = list(df_trace_list.values.tolist())

        context = {'tags': 'view_trace_info_list',
                   'total_traces': row_data[0][0] - 1,
                   'logid': log_id
                   }
    try:
        if trace_id is not None and len(trace_id) > 0:
            df_trace_detail = get_traceid_detail(log_id, trace_id)

            if 'Activity' in visual_type:
                posType = 0
            if 'Performer' in visual_type:
                posType = 1
            if 'Affiliation' in visual_type:
                posType = 5


            print('-------------------Visual type: %s. posType: %s' % (visual_type, posType))

            if posType is None:
                print('Error Visual Type! Please check in. Views.py/visualize_log() function.')

            if not ('-' in trace_id):
                trace = GetSingleTraceInfo(log_id.replace('xes', 'txt'), trace_id)
                if posType == 0 or posType == 1:
                    GenerateJsonFromInputTraces(log_id.replace('xes', 'txt'), trace_id, trace_id, posType)
                if posType == 5:
                    print('Will be implement soon')
                    pass
            else:
                trace = GetSingleTraceInfo(log_id.replace('xes', 'txt'), trace_id.split('-')[0])
                if posType == 0 or posType == 1:
                    GenerateJsonFromInputTraces(log_id.replace('xes', 'txt'), trace_id.split('-')[0], trace_id.split('-')[-1], posType)
                if posType == 5:
                    print('Will be implement soon')
                    pass

            if trace is not None:
                act_list, per_list, time_list = GetValueFromTrace(trace, 0), GetValueFromTrace(trace, 1), GetValueFromTrace(
                    trace, 2)

                trace_list = list(zip(act_list, per_list, time_list))

                context['traceid'] = trace_id
                context['act'] = act_list
                context['per'] = per_list
                context['time'] = time_list
                context['trace_range'] = range(len(act_list))
                context['trace_list'] = trace_list
                print(trace_list)
            # print('context: ', context)
    except:
        raise Http404("Trace does not exist")

    return render(request, 'adminlte/visualize_log.html', context)




