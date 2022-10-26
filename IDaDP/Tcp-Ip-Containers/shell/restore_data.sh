cd ../res || exit
rm data.json
cp data-backup.json data.json
rm data.db
cp data-backup.db data.db
cd ../shell/ || exit
