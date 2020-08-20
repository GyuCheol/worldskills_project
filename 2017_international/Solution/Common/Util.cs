using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Common
{
    public static class Util
    {
        public static readonly MD5 md5 = MD5.Create();

        public static string GetMD5String(String text)
        {
            byte[] hash = md5.ComputeHash(Encoding.Default.GetBytes(text));
            
            return string.Join("", hash.Select(x => $"{x:x}"));
        }

        public static int GetAge(DateTime birthdate)
        {
            int birth = int.Parse($"{birthdate:yyyyMMdd}");
            int now = int.Parse($"{DateTime.Now:yyyyMMdd}");

            return (now - birth) / 10000;
        }

    }
}
