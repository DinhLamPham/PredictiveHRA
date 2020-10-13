from AdminLTE.Helper.MySQL import sqlSyntax, mySQLSettings
from AdminLTE.Helper.MySQL.mySQLSettings import *
import pandas as pd




# def UpdateFinishFuntionInDB(funcId, _output):
#     _tblName = GVar.tblFunctionList
#     _headerCondition = 'id'
#     _valueCondition = funcId
#     _headerUpdate = ['output', 'run']
#     _valueUpdate = [_output, 'finished']
#     UpdateTable(_tblName, _headerCondition, _valueCondition, _headerUpdate, _valueUpdate)


# def SetFuntionIsRunning(_function, _status):
#     _tblName = tblFunctionList
#     _headerCondition = 'name'
#     _valueCondition = _function
#     _headerUpdate = ['run']
#     _valueUpdate = [_status]
#     UpdateTable(_tblName, _headerCondition, _valueCondition, _headerUpdate, _valueUpdate)


# def tblToDataFrame(_tblName):
#     sql = "SELECT * FROM " + _tblName
#     connection = mySQLSettings.OpenConnection()
#     return pd.read_sql(sql, connection)
#
#
# def GetFunctionStatus(_status):
#     sql = "SELECT * FROM " + tblFunctionList + " WHERE " + columnRunHeader + " ='" + _status + "'"
#     connection = mySQLSettings.OpenConnection()
#     df = pd.read_sql(sql, connection)
#     result = zip(df['id'], df['run'])
#     return result


# Get cell value in table and return in dataframe
def GetCellValue(_columnValue, _tblName, _columnCondition, _valueCondition):
    condList = " and ".join([x + "='" + y + "'" for x, y in zip(_columnCondition, _valueCondition)])

    sql = "SELECT " + _columnValue + " FROM " + _tblName + " WHERE " + condList
    connection = mySQLSettings.OpenConnection()
    df = pd.read_sql(sql, connection)
    return df[_columnValue]


def SelectColsWithConditions(_columnValue, _tblName, _columnCondition, _valueCondition):
    colList = ", ".join(_columnValue)
    if len(_columnCondition) > 1:
        condList = " and ".join([x + "='" + y + "'" for x, y in zip(_columnCondition, _valueCondition)])
    else:
        condList = _columnCondition[0] + "='" + _valueCondition[0] + "'"

    sql = "SELECT " + colList + " FROM " + _tblName + " WHERE " + condList
    connection = mySQLSettings.OpenConnection()
    df = pd.read_sql(sql, connection)
    return df


def SelectColWithCondition(_columnValue, _tblName, _columnCondition, _valueCondition):
    sql = "SELECT " + _columnValue + " FROM " + _tblName + " WHERE " + _columnCondition + "='" + str(_valueCondition) + "'"
    print('sql cmd: ', sql)
    connection = mySQLSettings.OpenConnection()
    df = pd.read_sql(sql, connection)
    return df


# Update Table:
def UpdateTable(_tblName, _headerCondition, _valueCondition, _headersUpdate, _valuesUpdate):
    sql = sqlSyntax.UpdateTableSQLCommands(_tblName, _headerCondition, _valueCondition, _headersUpdate, _valuesUpdate)
    RunSQL(sql)


def InsertRowToTable(_tblName, _header, _rowsTuple):
    tmp = ["\"" + str(x) + "\"" for x in _rowsTuple]
    strValue = ", ".join(tmp)
    sql = r"INSERT INTO " + _tblName + " (" + ",".join(_header) \
          + ") VALUES " + "(" + strValue + ")"
    RunSQL(sql)


def DeleteDatainTable(_tblName):
    sql = "DELETE FROM " + _tblName
    RunSQL(sql)


def InsertToLogFileTbl(_id, _name, _description, _url, _size, _date):
    try:
        _rowsTuple = (_id, _name, _description, _url, _size, _date)
        InsertRowToTable(tbl_logfile_name, tbl_logfile_header, _rowsTuple)

        _rowsTuple = (_id, _url, 0)
        InsertRowToTable(tbl_convertedlog_name, tbl_convertedlog_header, _rowsTuple)
        return True
    except:
        return False




if __name__ == "__main__":
    InsertToLogFileTbl('a', 'bpi2018', 'test upload file',
                   'media/bpi2018.xes', '1.1', '5/5/2020')