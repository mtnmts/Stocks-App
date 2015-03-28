# "Get the stock lists


GUID_MARKER = "%%GUID%%"
TASE_MAIN_SEC_PAGE = r"http://www.tase.co.il/Eng/MarketData/Securities/Pages/Securities.aspx"
# Export types, 3 == CSV, 1 == EXCEl
TASE_CSV_PAGE = "http://www.tase.co.il/_layouts/Tase/ManagementPages/Export.aspx?sn=none&action=1&SubAction=0&GridId=94&CurGuid={" + \
    GUID_MARKER + "}&ExportType=3"
import requests
import re
import StringIO
import json


def get_guid():
    resp = requests.get(TASE_MAIN_SEC_PAGE)
    return re.finditer('.*CurGuid={(.*)}', resp.text).next().group(1)


def get_stock_csv():
    tase_url = TASE_CSV_PAGE.replace(GUID_MARKER, get_guid())
    log("Fetching CSV from " + tase_url)
    return '\r\n'.join(requests.get(tase_url).text.split('\r\n')[4:]).strip('\r\n')


def parse_stock_csv(stock_data):
    res = []
    for column in stock_data.split('\r\n'):
        header = column.split(',')
        res.append(header)
    return res


def log(s):
    print "[*] " + s


if __name__ == "__main__":
    csv = get_stock_csv()
    data = parse_stock_csv(csv)
    open('test\\output.json', 'wb').write(json.dumps(data))
