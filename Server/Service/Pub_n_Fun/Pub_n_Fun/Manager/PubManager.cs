using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Pub_n_Fun;
using Pub_n_Fun.EDM.tmp;

namespace Pub_n_Fun.Manager
{
    public static class PubManager
    {
        public static float CalculateOverAllRatingPub(string pubID)
        {
            try
            {
                List<customerOpinions> ops = new List<customerOpinions>();
                float sum = 0;
                float overallRating = 0;

                if (int.TryParse(pubID, out int tmp))
                {
                    using (var db = new RftKocsmaAppDBEntities())
                    {                        
                        ops = db.customerOpinions.SqlQuery("SELECT * FROM dbo.customerOpinions WHERE pubID=" + tmp + ";").ToList();

                        foreach (customerOpinions op in ops)
                        {
                            sum += op.rating;
                        }

                        if (ops.Count != 0)
                        {
                            overallRating = sum / ops.Count;
                        }
                        else overallRating = 0;
                    }

                    return overallRating;
                }
                else
                {
                    return 0;
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }
    }
}