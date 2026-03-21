import sqlite3

def check_accounts():
    try:
        conn = sqlite3.connect('c:/Users/user/Desktop/Alex travel/tms.db')
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM account")
        rows = cursor.fetchall()
        print("Accounts in database:")
        for row in rows:
            print(row)
        conn.close()
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    check_accounts()
