using Common;
using Common.DB;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace Session1_Importing
{
    class Program
    {
        static void Main(string[] args)
        {
            var lines = File.ReadAllLines("./WSC2017_TP09_M1_UserData_actual.csv");
            var context = new Session1Db();
            var officeMap = context.Offices.ToDictionary(x => x.Title);
            int id = 0;

            foreach (var line in lines)
            {
                string[] split = line.Split(',');
                int role = split[0][0] == 'A' ? 1 : 2;
                
                var user = new User() {
                    ID = ++id,
                    RoleID = role,
                    Email = split[1],
                    Password = Util.GetMD5String(split[2]),
                    FirstName = split[3],
                    LastName = split[4],
                    OfficeID = officeMap[split[5]].ID,
                    Birthdate = DateTime.Parse(split[6]),
                    Active = int.Parse(split[7]) == 1
                };

                context.Users.Add(user);
            }

            context.SaveChanges();
        }
    }
}
