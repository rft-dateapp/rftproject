using Pub_n_Fun.Cache;
using Pub_n_Fun.Error;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using Pub_n_Fun.Models;
using Pub_n_Fun.EDM;

namespace Pub_n_Fun
{
    public class PubnFunCore : IPubnFunCore
    {
        private static List<Models.Pub> Pubs = new List<Models.Pub>();
        private static List<EDM.Pubs> awe = new List<EDM.Pubs>()
                        {
                            new EDM.Pubs()
                            {
                                pubID = 1,
                                address = "Debrecen, Zákány utca 26.",
                                name = "Campus Hotel és Kollégium",
                                customerOpinions = new List<customerOpinions>()
                                {
                                    new customerOpinions()
                                    {
                                            opinion = "nincs meleg viz, szar a net, cuck fampus",
                                            rating = 1,
                                            pubID = 1,
                                    },
                                    new customerOpinions()
                                    {
                                            opinion = "uuuuhhhh pull the trigger! Aint nobody gona do it for you.",
                                            rating = 5,
                                            pubID = 1,
                                    },
                                },
                                latitude = (float)47.545093,
                                longitude = (float)21.640869,
                            },
                            new EDM.Pubs()
                            {
                                pubID = 2,
                                address = "isten háta mögöttön is túl",
                                name = "kurtafarkú malac kcosmája",
                                latitude = 0,
                                longitude = 0,
                            },
                            new EDM.Pubs()
                            {
                                pubID = 3,
                                address = "szomszéd",
                                name = "szomszéd",
                                latitude = 100,
                                longitude = 100,
                            },
                        };

        public void AddOpinion(customerOpinions OpinionToBeAdded)
        {
            try
            {
                using (var db = new RftKocsmaAppDBEntities())
                {
                    db.customerOpinions.Add(OpinionToBeAdded);
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public void AddPub(EDM.Pubs PubToBeAdded)
        {
            try
            {
                //Pubs.Add(PubToBeAdded);

                using (var db = new EDM.RftKocsmaAppDBEntities())
                {
                    db.Pubs.Add(PubToBeAdded);

                    db.SaveChanges();
                }
            }
            catch(Exception e)
            {
                throw e;
            }
        }

        public void DeleteOpinionByID(string opinionID)
        {
            try
            {
                using (var db = new RftKocsmaAppDBEntities())
                {
                    db.customerOpinions.Remove(db.customerOpinions.Find(opinionID));
                    db.SaveChanges();
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public void DeletePubByID(string pubID)
        {
            try
            {
                using (var db = new RftKocsmaAppDBEntities())
                {
                    db.Pubs.Remove(db.Pubs.Find(pubID));
                    db.SaveChanges();
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public List<EDM.customerOpinions> GetAllOpinionListAboutPubByID(string pubID)
        {
            try
            {
                if (int.TryParse(pubID, out int tmp))
                {
                    using (var db = new RftKocsmaAppDBEntities())
                    {
                        return db.customerOpinions.Where(p => p.pubID == tmp).ToList(); ;
                    }
                }
                else
                {
                    return null;
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public List<EDM.Pubs> GetAllPubList()
        {
            try
            {                
                using (var db = new RftKocsmaAppDBEntities())
                {                    
                    return db.Pubs.ToList();
                }
            }
            catch (Exception e)
            {

                throw e;
            }
            
        }

        public customerOpinions GetCustomerOpinion(string opinionID)
        {
            try
            {
                using ( var db = new RftKocsmaAppDBEntities())
                {
                    return db.customerOpinions.Find(opinionID);
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public EDM.Pubs GetPubByID(string pubID)
        {
            try
            {
                using (var db = new RftKocsmaAppDBEntities())
                {
                    return db.Pubs.Find(pubID);
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public string GetSystemDate(string token)
        {
            try
            {
                if (CacheManager.ValidateToken(token))
                {
                    return DateTime.Now.ToString();
                }
                else
                {
                    throw new InvalidTokenException("Invalid given token at GetSystemDate");
                }
            }
            catch(Exception e)
            {
                throw e;
            }
        }

        public void UpdateOpinion(customerOpinions OpinionToBeUpdated)
        {
            throw new NotImplementedException();
        }

        public void UpdatePub(EDM.Pubs PubToBeUpdated)
        {
            throw new NotImplementedException();
        }
    }
}
