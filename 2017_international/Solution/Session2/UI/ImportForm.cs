using Common.DB;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace Session2.UI
{
    public partial class ImportForm : Common.UI.TemplateForm
    {
        public ImportForm()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Import Button Click Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnImport_Click(object sender, EventArgs e)
        {
            openFileDialog1.FileName = null;

            openFileDialog1.ShowDialog();

            if (openFileDialog1.FileName != null)
            {
                txtPath.Text = openFileDialog1.FileName;

                var lines = File.ReadAllLines(openFileDialog1.FileName);
                int duplicated = 0;
                int missing = 0;
                int success = 0;
                var aircraftSet = context_s2.Aircrafts.Select(x => x.ID).ToHashSet();
                var routeMap = new Dictionary<string, Route>();
                var scheduleMap = context_s2.Schedules.ToDictionary(x => $"{x.FlightNumber}_{x.Date:yyyyMMdd}_{x.Time:hhmm}");
                int aircraftId;
                decimal price;
                
                foreach (var route in context_s2.Routes)
                {
                    string key = $"{route.Airport.IATACode}_{route.Airport1.IATACode}";

                    if (routeMap.ContainsKey(key) == false)
                    {
                        routeMap.Add(key, route);
                    }
                }

                foreach (var line in lines)
                {
                    var split = line.Split(',');
                    string routeKey = $"{split[4]}_{split[5]}";
                    DateTime date;
                    TimeSpan time;

                    if (split.Length != 9)
                    {
                        // Wrong CSV Columns
                        Console.WriteLine($"Missing Length 9 {line}");
                        missing++; continue;
                    } else if (split[0] != "ADD" && split[0] != "EDIT")
                    {
                        // Wrong Record Type
                        Console.WriteLine($"Wrong Record Type {line}");
                        missing++; continue;
                    } else if (DateTime.TryParse(split[1], out date) == false)
                    {
                        // Wrong Date Format
                        Console.WriteLine($"Wrong Date Format {line}");
                        missing++; continue;
                    } else if (TimeSpan.TryParse(split[2], out time) == false)
                    {
                        // Wrong Time Format
                        Console.WriteLine($"Wrong Time Format {line}");
                        missing++; continue;
                    }

                    var key = $"{split[3]}_{date:yyyyMMdd}_{time:hhmm}";

                    if (scheduleMap.ContainsKey(key) && split[0] == "ADD")
                    {
                        // Duplicated ADD
                        Console.WriteLine($"Dueplicated {line}");
                        duplicated++; continue;
                    } else if (scheduleMap.ContainsKey(key) == false && split[0] == "EDIT")
                    {
                        // missing EDIT
                        Console.WriteLine($"Missing edit {line}");
                        missing++; continue;
                    } else if (routeMap.ContainsKey(routeKey) == false)
                    {
                        // missing IATA Code
                        Console.WriteLine($"Missing iata {line}");
                        missing++; continue;
                    } else if (int.TryParse(split[6], out aircraftId) == false || aircraftSet.Contains(aircraftId) == false)
                    {
                        // missing aircraft
                        Console.WriteLine($"Missing aircraft {line}");
                        missing++; continue;
                    } else if (decimal.TryParse(split[7], out price) == false)
                    {
                        // missing price
                        Console.WriteLine($"Wrong price {line}");
                        missing++; continue;
                    } else if (split[8] != "OK" && split[8] != "CANCELED")
                    {
                        // missing confirmed
                        Console.WriteLine($"Wrong confirmed {line}");
                        missing++; continue;
                    }

                    Schedule schedule;

                    if (split[0] == "ADD")
                    {
                        schedule = new Schedule() {
                            FlightNumber = split[3],
                            Date = date,
                            Time = time
                        };
                        scheduleMap.Add(key, schedule);
                        context_s2.Schedules.Add(schedule);
                    } else
                    {
                        schedule = scheduleMap[key];
                    }

                    schedule.AircraftID = aircraftId;
                    schedule.EconomyPrice = price;
                    schedule.RouteID = routeMap[routeKey].ID;
                    schedule.Confirmed = split[8] == "OK";

                    Console.WriteLine($"Successfully {line}");

                    success++;
                }

                context_s2.SaveChanges();

                lbApplied.Text = success.ToString();
                lbDuplicated.Text = duplicated.ToString();
                lbMIssing.Text = missing.ToString();

                MessageInfo("The task is successfully executed!");

            }
        }
    }
}
