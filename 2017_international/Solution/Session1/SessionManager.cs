using Common.DB;
using Common.UI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Session1
{
    public static class SessionManager
    {
        public static Session1Db context = TemplateForm.context;

        public static User Session { get; set; }
        public static UserLog SessionLog { get; set; }

    }
}
