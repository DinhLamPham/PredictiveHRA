import pandas as pd

from AdminLTE.Helper.MySQL import mySQLSettings
from AdminLTE.Helper.MySQL.mySQLSettings import *


def get_trace_info_list(_logid):
    sql = "SELECT COUNT(*) FROM " + tbl_trace_info + " WHERE logid='" + _logid + "'"
    connection = mySQLSettings.OpenConnection()
    if connection is not None:
        df = pd.read_sql(sql, connection)
        mySQLSettings.CloseConnection(connection)
        return df
    else:
        return None

def get_traceid_detail(_logid, _traceid):
    sql = "SELECT * FROM " + tbl_trace_info + " WHERE logid='" + _logid + "' and traceid='" + str(_traceid) + "'"
    connection = mySQLSettings.OpenConnection()
    if connection is not None:
        df = pd.read_sql(sql, connection)
        mySQLSettings.CloseConnection(connection)
        return df
    else:
        return None


def get_log_list():
    sql = "SELECT * FROM " + tbl_logfile_name
    connection = mySQLSettings.OpenConnection()
    if connection is not None:
        df = pd.read_sql(sql, connection)
        mySQLSettings.CloseConnection(connection)
        return df
    else:
        return None


def get_converted_list():
    sql = "SELECT * FROM " + tbl_convertedlog_name
    connection = mySQLSettings.OpenConnection()
    if connection is not None:
        df = pd.read_sql(sql, connection)
        mySQLSettings.CloseConnection(connection)
        return df
    else:
        return None
