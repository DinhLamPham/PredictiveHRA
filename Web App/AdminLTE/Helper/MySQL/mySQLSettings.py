import pymysql

my_host = 'localhost'
my_user = 'root'
my_password = '123456'
my_db = 'PMoW'

tbl_logfile_header = ['ID', 'Name', 'Description', 'URL', 'Size', 'Date']
tbl_logfile_name = 'logfile'

tbl_convertedlog_header = ['ID', 'Url', 'Status']
tbl_convertedlog_name = 'converted_log'

tbl_trace_info = 'trace_info'
tbl_trace_info_header = ['logid', 'traceid', 'n_activity', 'n_performer', 'duration']

tbl_json_cache = 'json_cache'
tbl_json_cache_header = ['logid', 'traces', 'jsondata']


def OpenConnection():
    try:
        connection = pymysql.connect(host=my_host, user=my_user, password=my_password, db=my_db)
        return connection
    except:
        return None


def CloseConnection(connection):
    connection.close()


def RunSQL(_sql):
    connection = OpenConnection()
    cursor = connection.cursor()
    cursor.execute(_sql)
    connection.commit()
    CloseConnection(connection)
