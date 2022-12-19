import pandas as pd
import openpyxl
import sqlite3

file_name = 'v2_hotel_excel.xlsx'

conn = sqlite3.connect('./hotel.db')
cursor = conn.cursor()
# for i in range(1, 7):
#     print("{:=^90}".format(f' SQL {i}  '))
#     cursor.execute(f'select * from task_{i}')
#     head = [description[0] for description in cursor.description]
#     print(("{:<15} " * len(head)).format(*head))
#     for items in cursor.fetchall():
#         print(("{:<15} " * len(items)).format(*items))


# # with pd.ExcelWriter(file_name) as writer:
# #     for i in range(1, 7):
# #         cursor.execute(f'select * from task_{i}')
# #         df = pd.DataFrame(cursor.fetchall() ,columns=[description[0] for description in cursor.description])
# #         df.to_excel(writer, sheet_name=f'hotel_sheet_{i}')

# qwe = 0
# table_names = ['rooms', 'booking', 'customers', 'hotels']
# with pd.ExcelWriter(file_name) as writer:
#     for i, names in enumerate(table_names):
#         cursor.execute(f'select * from {names}')
#         columns = [description[0] for description in cursor.description]
#         df = pd.DataFrame(cursor.fetchall(), columns=columns)
#         df.to_excel(writer, startcol=qwe ,sheet_name=f'hotel_sheet', index=False)
#         qwe = qwe + len(columns)




cursor.execute(f'select * from rooms')
columns = [description[0] for description in cursor.description]
df = pd.DataFrame(cursor.fetchall() ,columns=columns)
pv = df[(df.price < 20) & (df.max_persons == 2)].pivot_table(index=['id'], values=['price'])\
                                                .sort_values(by=["price"])
# print(pv)
# pv.to_excel(writer, sheet_name=f'pv1')
import datetime
cursor.execute(f'select * from booking')
columns = [description[0] for description in cursor.description]
df = pd.DataFrame(cursor.fetchall() ,columns=columns)
df['accommodation_end'] = pd.to_datetime(df['accommodation_end'])
mask = (df['accommodation_end'] <=datetime.datetime.now()) & (df['accommodation_end'] >= datetime.datetime.now() - datetime.timedelta(days=14))
df = df.loc[mask]
del df['hotel_id']
# print(df.to_string(index=False))

cursor.execute(f'select * from booking')
columns = [description[0] for description in cursor.description]
df = pd.DataFrame(cursor.fetchall() ,columns=columns)
df['register_date'] = pd.to_datetime(df['register_date'])
cursor.execute(f'select * from rooms')
columns = [description[0] for description in cursor.description]
df1 = pd.DataFrame(cursor.fetchall() ,columns=columns)
res = pd.merge(df, df1, left_on='room_id', right_on='id')
res['Доход'] = ((df['accommodation_end'] - df['register_date']).dt.days) * res['price']
print(res.pivot_table(index='id_x', values=['Доход'], aggfunc='sum',margins = True, margins_name='Всего'))
