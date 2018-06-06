package com.kapouter.api.model

data class QueueProject(val id: Int,
                        val name: String,
                        val position_in_queue: Int,
                        val pattern_name: String,
                        val short_pattern_name: String,
                        val pattern_author_name: String,
                        val best_photo: Photo?,
                        val yarn_name: String,
                        val notes: String)