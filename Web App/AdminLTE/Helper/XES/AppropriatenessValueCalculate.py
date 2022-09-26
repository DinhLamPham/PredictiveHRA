import os
import sys
import traceback
import csv

from AdminLTE.Helper.Commons.Variables import key_connect_node, key_connect_inside


def show_log_error(e):
    exc_type, exc_obj, exc_tb = sys.exc_info()
    error_file = os.path.split(exc_tb.tb_frame.f_code.co_filename)[1]
    error_function = traceback.extract_tb(sys.exc_info()[-1], 1)[0][2]
    print('---------BEGIN ERROR--------------')
    print(exc_type, e, f'\nFile: {error_file} Function: {error_function}. Line: {exc_tb.tb_lineno}')
    print('---------END ERROR--------------')

def read_csv_trace_to_memory(txt_file):
    try:
        content = None
        txt_file = os.path.join(os.getcwd(), 'ConvertXESToCSV', txt_file)
        with open(txt_file) as f:
            content = [line.rstrip() for line in f]

        act_dict, per_dict, act_per_dict = {}, {}, {}
        act_ap_dict = {}
        for traces in content:
            events = traces.split(key_connect_node)
            for event in events:
                node = event.split(key_connect_inside)
                act, per = node[0], node[1]

                act_dict[act] = act_dict.get(act, 0) + 1
                per_dict[per] = per_dict.get(per, 0) + 1

                act_per_dict[f'{act}{key_connect_inside}{per}'] = act_per_dict.get(f'{act}{key_connect_inside}{per}', 0) + 1

        return act_dict, per_dict, act_per_dict

    except Exception as e:
        show_log_error(e)


def save_dict_to_csv(_input_dict, _output_file):
    import pandas as pd
    try:
        df = pd.DataFrame(list(_input_dict.items()))
        list_result = list(_input_dict.items())

        df.to_csv(os.path.join(os.getcwd(), 'ConvertXESToCSV', _output_file))
        print(f'---------------Generated result to {_output_file}')

    except Exception as e:
        show_log_error(e)


def calculateAP():
    try:
        act_dict, per_dict, act_per_dict = read_csv_trace_to_memory('bpi2012.txt')
        act_ap_value, per_ap_value = {}, {}

        for act_per, occurence in act_per_dict.items():
            act, per = act_per.split(key_connect_inside)[0], act_per.split(key_connect_inside)[1]
            act_ap_value[act_per] = occurence/act_dict[act]
            per_ap_value[f'{per}{key_connect_inside}{act}'] = occurence/per_dict[per]

        # Save results
        save_dict_to_csv(act_ap_value, 'appropriateness of activities.csv')
        save_dict_to_csv(per_ap_value, 'appropriateness of performers.csv')


    except Exception as e:
        show_log_error(e)

calculateAP()


